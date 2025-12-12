package seneca.pmugisha3.bookmanager;

import static seneca.pmugisha3.bookmanager.util.Constants.EXTRA_BOOK;
import static seneca.pmugisha3.bookmanager.util.Constants.EXTRA_PHOTO;
import static seneca.pmugisha3.bookmanager.util.Constants.KEY_AUTHOR;
import static seneca.pmugisha3.bookmanager.util.Constants.KEY_TITLE;
import static seneca.pmugisha3.bookmanager.util.Constants.KEY_YEAR;
import static seneca.pmugisha3.bookmanager.util.Constants.LOG_TAG_LIFECYCLE;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import seneca.pmugisha3.bookmanager.models.Book;
import seneca.pmugisha3.bookmanager.util.IntentUtils;

/**
 * This activity demonstrates the use of common intents for sharing and searching for a book.
 */
public class CommonIntentActivity extends AppCompatActivity implements View.OnClickListener {
  // UI elements
  private TextView tvTitle, tvAuthor, tvYear;
  private ImageView bookPhoto;

  // Data received from the previous activity
  private Book receivedBook;
  private Bitmap photoBitmap;

  /**
   * Initializes the activity, its views, and retrieves the book and photo from the intent.
   *
   * @param savedInstanceState If the activity is being re-initialized, this Bundle contains the most recent data.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_common_intent);

    Log.d(LOG_TAG_LIFECYCLE, "Common Intent Activity - on Create");

    // Initialize views
    tvTitle = findViewById(R.id.tv_title);
    tvAuthor = findViewById(R.id.tv_author);
    tvYear = findViewById(R.id.tv_year);
    bookPhoto = findViewById(R.id.iv_book_photo);

    Button btnShare = findViewById(R.id.btn_share);
    Button btnSearch = findViewById(R.id.btn_search);

    if (savedInstanceState != null) {
      receivedBook = savedInstanceState.getParcelable(EXTRA_BOOK);
      photoBitmap = savedInstanceState.getParcelable(EXTRA_PHOTO);
      tvTitle.setText(savedInstanceState.getString(KEY_TITLE));
      tvAuthor.setText(savedInstanceState.getString(KEY_AUTHOR));
      tvYear.setText(savedInstanceState.getString(KEY_YEAR));
    } else {
      receivedBook = getIntent().getParcelableExtra(EXTRA_BOOK);
      photoBitmap = getIntent().getParcelableExtra(EXTRA_PHOTO);

      if (receivedBook != null) {
        displayBookInfo();
      }
    }

    if (photoBitmap != null) {
      bookPhoto.setImageBitmap(photoBitmap);
    }

    // Add listeners to button
    btnShare.setOnClickListener(this);
    btnSearch.setOnClickListener(this);
  }

  /**
   * Displays the book's information in the UI with labels.
   */
  private void displayBookInfo() {
    tvTitle.setText(String.format("%s: %s", getString(R.string.book_title_label), receivedBook.getTitle()));
    tvAuthor.setText(String.format("%s: %s", getString(R.string.book_author_label), receivedBook.getAuthor()));
    tvYear.setText(String.format(Locale.getDefault(), "%s: %d", getString(R.string.book_year_label), receivedBook.getYear()));
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.btn_share) {
      IntentUtils.shareBook(this, receivedBook);
    } else if (id == R.id.btn_search) {
      IntentUtils.searchBook(this, receivedBook);
    }
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(EXTRA_BOOK, receivedBook);
    outState.putParcelable(EXTRA_PHOTO, photoBitmap);
    outState.putString(KEY_TITLE, tvTitle.getText().toString());
    outState.putString(KEY_AUTHOR, tvAuthor.getText().toString());
    outState.putString(KEY_YEAR, tvYear.getText().toString());
  }
}
