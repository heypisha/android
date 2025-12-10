package seneca.pmugisha3.bookmanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import seneca.pmugisha3.bookmanager.models.Book;

/**
 * Displays the details of a book and allows the user to take a photo.
 */
public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener {
  // UI elements for displaying book details
  private TextView tvTitle, tvAuthor, tvYear;
  private ImageView bookPhoto;

  // The book object being displayed
  private Book receivedBook;

  // The photo taken by the user
  private Bitmap photoBitmap;

  // Launcher for the camera activity
  private ActivityResultLauncher<Intent> cameraLauncher;

  // Keys for saving and retrieving instance state
  private static final String EXTRA_PHOTO = "photo";
  private static final String EXTRA_BOOK = "currentBook";

  /**
   * Initializes the activity, its views, and retrieves the book details from the intent.
   *
   * @param savedInstanceState If the activity is being re-initialized, this Bundle contains the most recent data.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_book_detail);

    Log.d("lifecycle", "Book Detail Activity - on Create");

    // Initialize views
    tvTitle = findViewById(R.id.tvBookTitle);
    tvAuthor = findViewById(R.id.tvBookAuthor);
    tvYear = findViewById(R.id.tvBookYear);
    bookPhoto = findViewById(R.id.ivBookPhoto);

    Button btnTakePhoto = findViewById(R.id.btnTakePhoto);
    Button btnNext = findViewById(R.id.btnNext);

    // Setup the camera launcher to handle the result from the camera app
    cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
      if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getExtras() != null) {
        photoBitmap = (Bitmap) result.getData().getExtras().get("data");
        bookPhoto.setImageBitmap(photoBitmap);
      } else {
        Toast.makeText(this, "Photo capture cancelled or failed", Toast.LENGTH_SHORT).show();
      }
    });

    // Restore state from either the saved instance or the intent
    if (savedInstanceState != null) {
      photoBitmap = savedInstanceState.getParcelable(EXTRA_PHOTO);
      receivedBook = savedInstanceState.getParcelable(EXTRA_BOOK);
    } else {
      receivedBook = getIntent().getParcelableExtra(EXTRA_BOOK);
    }

    // Display book info and photo if available
    if (receivedBook != null) {
      displayBookInfo();
    }
    if (photoBitmap != null) {
      bookPhoto.setImageBitmap(photoBitmap);
    }

    // Set click listeners for the buttons
    btnTakePhoto.setOnClickListener(this);
    btnNext.setOnClickListener(this);
  }

  /**
   * Displays the book's information in the UI.
   */
  private void displayBookInfo() {
    tvTitle.setText(String.format("%s: %s", getString(R.string.bda_title), receivedBook.getTitle()));
    tvAuthor.setText(String.format("%s: %s", getString(R.string.bda_author), receivedBook.getAuthor()));
    tvYear.setText(String.format(Locale.getDefault(), "%s: %d", getString(R.string.bda_year), receivedBook.getYear()));
  }

  /**
   * Launches an intent to capture an image using the device's camera.
   */
  private void dispatchTakePictureIntent() {
    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    cameraLauncher.launch(takePicture);
  }

  /**
   * Handles click events for the buttons in the activity.
   *
   * @param v The view that was clicked.
   */
  @Override
  public void onClick(View v) {
    int id = v.getId();

    if (id == R.id.btnTakePhoto) {
      dispatchTakePictureIntent();
    } else if (id == R.id.btnNext) {
      // Start the next activity, passing the book and photo
      Intent intent = new Intent(BookDetailActivity.this, CommonIntentActivity.class);
      intent.putExtra(EXTRA_BOOK, receivedBook);
      if (photoBitmap != null) {
        intent.putExtra(EXTRA_PHOTO, photoBitmap);
      }
      startActivity(intent);
    }
  }

  /**
   * Called when the activity will start interacting with the user.
   */
  @Override
  protected void onResume() {
    super.onResume();
    Log.d("lifecycle", "Book Detail  Activity - on Resume");
  }

  /**
   * Called when the activity is no longer visible to the user.
   */
  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d("lifecycle", "Book Detail  Activity - on Destroy");
  }

  /**
   * Called when the system is about to start resuming a previous activity.
   */
  @Override
  protected void onPause() {
    super.onPause();
    Log.d("lifecycle", "Book Detail  Activity - on Pause");
  }

  /**
   * Saves the state of the activity to a Bundle.
   *
   * @param outState The Bundle in which to place your saved state.
   */
  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    Log.d("lifecycle", "Book Detail  Activity - on Save Instance State");

    // Save the photo and book data
    outState.putParcelable(EXTRA_PHOTO, photoBitmap);
    outState.putParcelable(EXTRA_BOOK, receivedBook);
  }
}
