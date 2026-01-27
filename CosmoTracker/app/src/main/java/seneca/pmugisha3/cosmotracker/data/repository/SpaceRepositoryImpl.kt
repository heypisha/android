package seneca.pmugisha3.cosmotracker.data.repository

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import seneca.pmugisha3.cosmotracker.data.local.database.CosmoDatabase
import seneca.pmugisha3.cosmotracker.data.local.entity.FavoriteEventEntity
import seneca.pmugisha3.cosmotracker.data.mapper.toFavoriteEntity
import seneca.pmugisha3.cosmotracker.data.remote.RetrofitClient
import seneca.pmugisha3.cosmotracker.data.remote.model.ApodResponse
import seneca.pmugisha3.cosmotracker.data.remote.model.EonetResponse
import seneca.pmugisha3.cosmotracker.data.remote.model.EventDto

class SpaceRepositoryImpl(context: Context) : SpaceRepository {

    private val nasaApi = RetrofitClient.nasaApi
    private val eonetApi = RetrofitClient.eonetApi
    private val favoriteEventDao = CosmoDatabase.getDatabase(context).favoriteEventDao()

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val TAG = "SpaceRepositoryImpl"

    init {
        ensureAnonymousSignIn()
    }

    private fun ensureAnonymousSignIn() {
        // Sign in anonymously if not already signed in
        if (firebaseAuth.currentUser == null) {
            firebaseAuth.signInAnonymously()
                .addOnSuccessListener {
                    Log.d(TAG, "Anonymous sign-in successful: ${it.user?.uid}")
                }
                .addOnFailureListener { e ->
                    Log.e(
                        TAG,
                        "Anonymous sign-in failed. Check if Anonymous Auth is enabled in Firebase Console.",
                        e
                    )
                }
        } else {
            Log.d(TAG, "Already signed in as: ${firebaseAuth.currentUser?.uid}")
        }
    }

    // Remote data
    override suspend fun getApod(): Resource<ApodResponse> {
        return try {
            val response = nasaApi.getApod()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error occurred", e)
        }
    }

    override suspend fun getEvents(status: String, limit: Int): Resource<EonetResponse> {
        return try {
            val response = eonetApi.getEvents(status, limit)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error occurred", e)
        }
    }

    // Local data (favorites)
    override fun getAllFavorites(): Flow<List<FavoriteEventEntity>> {
        return favoriteEventDao.getAllFavorites()
    }

    override suspend fun getFavoriteById(eventId: String): FavoriteEventEntity? {
        return favoriteEventDao.getFavoriteById(eventId)
    }

    override suspend fun addFavorite(event: EventDto) {
        val entity = event.toFavoriteEntity()
        favoriteEventDao.insertFavorite(entity)

        // Sync to Firebase
        syncFavoriteToFirebase(entity)
    }

    override suspend fun removeFavorite(eventId: String) {
        favoriteEventDao.deleteFavoriteById(eventId)

        // Remove from Firebase
        removeFavoriteFromFirebase(eventId)
    }

    override suspend fun isFavorite(eventId: String): Boolean {
        return favoriteEventDao.isFavorite(eventId)
    }

    // Firebase sync
    private suspend fun syncFavoriteToFirebase(favorite: FavoriteEventEntity) {
        try {
            val userId = firebaseAuth.currentUser?.uid ?: run {
                Log.w(TAG, "Cannot sync: User not signed in")
                return
            }

            val favoriteMap = mapOf(
                "id" to favorite.id,
                "title" to favorite.title,
                "description" to favorite.description,
                "category" to favorite.category,
                "date" to favorite.date,
                "latitude" to favorite.latitude,
                "longitude" to favorite.longitude,
                "isClosed" to favorite.isClosed,
                "savedAt" to favorite.savedAt
            )

            firestore.collection("users")
                .document(userId)
                .collection("favorites")
                .document(favorite.id)
                .set(favoriteMap)
                .await()

            Log.d(TAG, "Favorite synced to Firebase: ${favorite.id}")
        } catch (e: Exception) {
            Log.e(TAG, "Error syncing favorite to Firebase", e)
        }
    }

    private suspend fun removeFavoriteFromFirebase(eventId: String) {
        try {
            val userId = firebaseAuth.currentUser?.uid ?: return

            firestore.collection("users")
                .document(userId)
                .collection("favorites")
                .document(eventId)
                .delete()
                .await()

            Log.d(TAG, "Favorite removed from Firebase: $eventId")
        } catch (e: Exception) {
            Log.e(TAG, "Error removing favorite from Firebase", e)
        }
    }

    override suspend fun syncFavoritesToFirebase() {
        try {
            val userId = firebaseAuth.currentUser?.uid ?: return
            Log.d(TAG, "Syncing all favorites to Firebase - Not fully implemented")
        } catch (e: Exception) {
            Log.e(TAG, "Error syncing all favorites to Firebase", e)
        }
    }

    override suspend fun syncFavoritesFromFirebase() {
        try {
            val userId = firebaseAuth.currentUser?.uid ?: run {
                Log.w(TAG, "Cannot sync from Firebase: User not signed in")
                return
            }

            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("favorites")
                .get()
                .await()

            for (document in snapshot.documents) {
                val favorite = FavoriteEventEntity(
                    id = document.getString("id") ?: continue,
                    title = document.getString("title") ?: "",
                    description = document.getString("description"),
                    category = document.getString("category") ?: "",
                    date = document.getString("date") ?: "",
                    latitude = document.getDouble("latitude"),
                    longitude = document.getDouble("longitude"),
                    isClosed = document.getBoolean("isClosed") ?: false,
                    savedAt = document.getLong("savedAt") ?: System.currentTimeMillis()
                )

                favoriteEventDao.insertFavorite(favorite)
            }

            Log.d(TAG, "Synced ${snapshot.size()} favorites from Firebase")
        } catch (e: Exception) {
            Log.e(TAG, "Error syncing favorites from Firebase", e)
        }
    }
}
