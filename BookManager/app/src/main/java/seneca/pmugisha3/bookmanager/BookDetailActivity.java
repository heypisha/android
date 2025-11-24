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

public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener {
  private TextView tvTitle, tvAuthor, tvYear;
  private ImageView bookPhoto;

  private Book receivedBook;

  private Bitmap photoBitmap;

  private ActivityResultLauncher<Intent> cameraLauncher;

  private static final String EXTRA_PHOTO = "photo";
  private static final String EXTRA_BOOK = "currentBook";

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
    receivedBook = getIntent().getParcelableExtra(EXTRA_BOOK);
    if (receivedBook != null) {
      displayBookInfo();
    }

    // Restore book and photo on rotation
    if (savedInstanceState != null) {
      photoBitmap = savedInstanceState.getParcelable(EXTRA_PHOTO);
      receivedBook = savedInstanceState.getParcelable(EXTRA_BOOK);
      if (photoBitmap != null) {
        bookPhoto.setImageBitmap(photoBitmap);
      }
      if (receivedBook != null) {
        displayBookInfo();
      }
    }

    // Add listeners to buttons
    btnTakePhoto.setOnClickListener(this);
    btnNext.setOnClickListener(this);
  }

  private void displayBookInfo() {
    tvTitle.setText(String.format("%s: %s", getString(R.string.bda_title), receivedBook.getTitle()));
    tvAuthor.setText(String.format("%s: %s", getString(R.string.bda_author), receivedBook.getAuthor()));
    tvYear.setText(String.format(Locale.getDefault(), "%s: %d", getString(R.string.bda_year), receivedBook.getYear()));
  }

  private void dispatchTakePictureIntent() {
    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    cameraLauncher.launch(takePicture);
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();

    if (id == R.id.btnTakePhoto) {
      dispatchTakePictureIntent();
    } else if (id == R.id.btnNext) {
      Intent intent = new Intent(BookDetailActivity.this, CommonIntentActivity.class);
      intent.putExtra(EXTRA_BOOK, receivedBook);
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

    outState.putParcelable(EXTRA_PHOTO, photoBitmap);
    outState.putParcelable(EXTRA_BOOK, receivedBook);
  }

  @Override
  public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    Log.d("lifecycle", "Book Detail  Activity - on Restore Instance State");
    photoBitmap = savedInstanceState.getParcelable(EXTRA_PHOTO);
    if (photoBitmap != null) {
      bookPhoto.setImageBitmap(photoBitmap);
    }
  }
}