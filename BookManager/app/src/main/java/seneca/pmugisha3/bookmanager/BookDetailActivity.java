package seneca.pmugisha3.bookmanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

import seneca.pmugisha3.bookmanager.models.Book;

public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener {
  private TextView tvTitle, tvAuthor, tvYear;
  private ImageView bookPhoto;
  private Button btnTakePhoto, btnNext;

  private Book receivedBook;

  private Bitmap photoBitmap;

  private ActivityResultLauncher<Intent> cameraLauncher;

  private static final String KEY_PHOTO = "photo";

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
    btnTakePhoto = findViewById(R.id.btnTakePhoto);
    btnNext = findViewById(R.id.btnNext);

    // setup camera launcher
    cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
      if (result.getResultCode() == RESULT_OK && result.getData() != null) {
        Intent data = result.getData();
        Bundle extras = data.getExtras();
        if (extras != null) {
          photoBitmap = (Bitmap) extras.get("data");
          bookPhoto.setImageBitmap(photoBitmap);
        }
      } else {
        Toast.makeText(this, "Photo capture cancelled or failed", Toast.LENGTH_SHORT).show();
      }
    });

    // Get Book object from intent
    receivedBook = getIntent().getParcelableExtra("currentBook");
    if (receivedBook != null) {
      displayBookInfo();
    }

    // Restore photo on rotation
    if (savedInstanceState != null) {
      photoBitmap = (Bitmap) savedInstanceState.get(KEY_PHOTO);
      if (photoBitmap != null) {
        bookPhoto.setImageBitmap(photoBitmap);
      }
    }

    btnTakePhoto.setOnClickListener(this);
    btnNext.setOnClickListener(this);
  }

  private void displayBookInfo() {
    tvTitle.setText(String.format("Title: %s", receivedBook.getTitle()));
    tvAuthor.setText(String.format("Author: %s", receivedBook.getAuthor()));
    tvYear.setText(String.format(Locale.getDefault(), "Year Published: %d", receivedBook.getYear()));
  }

  private void dispatchTakePictureIntent() {
    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    if (takePicture.resolveActivity(getPackageManager()) != null) {
      cameraLauncher.launch(takePicture);
    } else {
      Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();

    if (id == R.id.btnTakePhoto) {
      dispatchTakePictureIntent();
    } else if (id == R.id.btnNext) {
      Intent intent = new Intent(BookDetailActivity.this, CommonIntentActivity.class);
      intent.putExtra("currentBook", receivedBook);
      if (photoBitmap != null) {
        intent.putExtra("photo", photoBitmap);
      }
      startActivity(intent);
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.d("lifecycle", "Book Detail  Activity - on Resume");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d("lifecycle", "Book Detail  Activity - on Destroy");
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.d("lifecycle", "Book Detail  Activity - on Pause");
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    Log.d("lifecycle", "Book Detail  Activity - on Save Instance State");
    if (photoBitmap != null) {
      outState.putParcelable(KEY_PHOTO, photoBitmap);
    }
  }

  @Override
  public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    Log.d("lifecycle", "Book Detail  Activity - on Restore Instance State");
    photoBitmap = savedInstanceState.getParcelable(KEY_PHOTO);
    if (photoBitmap != null) {
      bookPhoto.setImageBitmap(photoBitmap);
    }
  }
}