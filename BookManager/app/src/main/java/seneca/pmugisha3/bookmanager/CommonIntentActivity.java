package seneca.pmugisha3.bookmanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import seneca.pmugisha3.bookmanager.models.Book;

/**
 * This activity demonstrates the use of common intents for sharing and searching for a book.
 */
public class CommonIntentActivity extends AppCompatActivity implements View.OnClickListener {
  // Keys for saving instance state
  private static final String KEY_TITLE = "title";
  private static final String KEY_AUTHOR = "author";
  private static final String KEY_YEAR = "year";
  // Keys for passing data between activities
  private final static String EXTRA_BOOK = "currentBook";
  private final static String EXTRA_PHOTO = "photo";
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

    Log.d("lifecycle", "Book Detail Activity - on Create");

    // Initialize views
    tvTitle = findViewById(R.id.tv_title);
    tvAuthor = findViewById(R.id.tv_author);
    tvYear = findViewById(R.id.tv_year);
    bookPhoto = findViewById(R.id.iv_book_photo);

    Button btnShare = findViewById(R.id.btn_share);
    Button btnSearch = findViewById(R.id.btn_search);

    // Restore state from either the saved instance or the intent
    if (savedInstanceState != null) {
      tvTitle.setText(savedInstanceState.getString(KEY_TITLE));
      tvAuthor.setText(savedInstanceState.getString(KEY_AUTHOR));
      tvYear.setText(savedInstanceState.getString(KEY_YEAR));
      photoBitmap = savedInstanceState.getParcelable(EXTRA_PHOTO);
    } else {
      receivedBook = getIntent().getParcelableExtra(EXTRA_BOOK);
      photoBitmap = getIntent().getParcelableExtra(EXTRA_PHOTO);

      if (receivedBook != null) {
        tvTitle.setText(receivedBook.getTitle());
        tvAuthor.setText(receivedBook.getAuthor());
        tvYear.setText(String.valueOf(receivedBook.getYear()));
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
   * Handles click events for the buttons in the activity.
   *
   * @param v The view that was clicked.
   */
  @Override
  public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.btn_share) {
      shareBook();
    } else if (id == R.id.btn_search) {
      searchBook();
    }
  }

  /**
   * Creates and launches a web search intent for the book's title.
   */
  private void searchBook() {
    String query = Uri.encode(receivedBook.getTitle());
    String url = "https://www.google.com/search?q=" + query;
    Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    startActivity(browser);
  }

  /**
   * Creates and launches a share intent to share the book's details.
   */
  private void shareBook() {
    String shareText = String.format(Locale.getDefault(), "%s: %s, %s %s (%d)", getString(R.string.check_out_this_book), receivedBook.getTitle(), getString(R.string.by), receivedBook.getAuthor(), receivedBook.getYear());
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.setType("text/plain");
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.book_recommendation));
    shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
    Intent chooser = Intent.createChooser(shareIntent, getString(R.string.share_book_via));
    startActivity(chooser);
  }
}
