package seneca.pmugisha3.bookmanager;

import static seneca.pmugisha3.bookmanager.util.Constants.EXTRA_BOOK;
import static seneca.pmugisha3.bookmanager.util.Constants.KEY_AUTHOR;
import static seneca.pmugisha3.bookmanager.util.Constants.KEY_TITLE;
import static seneca.pmugisha3.bookmanager.util.Constants.KEY_YEAR;
import static seneca.pmugisha3.bookmanager.util.Constants.LOG_TAG_LIFECYCLE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import seneca.pmugisha3.bookmanager.models.Book;

/**
 * The main activity of the application, where users can input book details.
 */
public class MainActivity extends AppCompatActivity {

  // EditText fields for book details
  private EditText titleInput, authorInput, yearInput;

  /**
   * Initializes the activity, sets up the user interface, and handles saved state.
   *
   * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
   *                           this Bundle contains the data it most recently supplied in onSaveInstanceState.
   *                           Otherwise, it is null.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    Log.d(LOG_TAG_LIFECYCLE, "Main Activity - on Create");

    // Initialize UI components
    titleInput = findViewById(R.id.bookTitle);
    authorInput = findViewById(R.id.authorName);
    yearInput = findViewById(R.id.yearPublished);
    Button btnNext = findViewById(R.id.btnNext);

    // Restore saved state if available
    if (savedInstanceState != null) {
      titleInput.setText(savedInstanceState.getString(KEY_TITLE));
      authorInput.setText(savedInstanceState.getString(KEY_AUTHOR));
      yearInput.setText(savedInstanceState.getString(KEY_YEAR));
    }

    // Set a click listener for the 'Next' button
    btnNext.setOnClickListener(v -> {
      // Get user input
      String title = titleInput.getText().toString().trim();
      String author = authorInput.getText().toString().trim();
      String yearStr = yearInput.getText().toString().trim();

      // Validate input fields
      if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty()) {
        Toast.makeText(this, R.string.error_missing_fields, Toast.LENGTH_SHORT).show();
        return;
      }

      try {
        // Create a new Book object and start the detail activity
        int year = Integer.parseInt(yearStr);
        Book book = new Book(title, author, year);
        Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
        intent.putExtra(EXTRA_BOOK, book);
        startActivity(intent); // Pass the book details to the next activity
      } catch (NumberFormatException e) {
        // Show an error message for invalid year format
        Toast.makeText(this, "Invalid year format", Toast.LENGTH_SHORT).show();
      }
    });
  }


  /**
   * Called when the activity will start interacting with the user.
   */
  @Override
  protected void onResume() {
    super.onResume();
    Log.d(LOG_TAG_LIFECYCLE, "Main Activity - on Resume");
  }

  /**
   * Called when the activity is no longer visible to the user.
   */
  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d(LOG_TAG_LIFECYCLE, "Main Activity - on Destroy");
  }

  /**
   * Called when the system is about to start resuming a previous activity.
   */
  @Override
  protected void onPause() {
    super.onPause();
    Log.d(LOG_TAG_LIFECYCLE, "Main Activity - on Pause");
  }

  /**
   * Saves the state of the activity to a Bundle.
   *
   * @param outState The Bundle in which to place your saved state.
   */
  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    Log.d(LOG_TAG_LIFECYCLE, "Main Activity - in onSaveInstanceState");
    // Save the current input values
    outState.putString(KEY_TITLE, titleInput.getText().toString());
    outState.putString(KEY_AUTHOR, authorInput.getText().toString());
    outState.putString(KEY_YEAR, yearInput.getText().toString());
  }
}
