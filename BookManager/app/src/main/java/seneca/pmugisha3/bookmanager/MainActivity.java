package seneca.pmugisha3.bookmanager;

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

public class MainActivity extends AppCompatActivity {

  private EditText titleInput, authorInput, yearInput;

  private static final String KEY_TITLE = "title";
  private static final String KEY_AUTHOR = "author";
  private static final String KEY_YEAR = "year";
  private static final String EXTRA_BOOK="currentBook";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    Log.d("lifecycle", "Main Activity - on Create");

    titleInput = findViewById(R.id.bookTitle);
    authorInput = findViewById(R.id.authorName);
    yearInput = findViewById(R.id.yearPublished);
    Button btnNext = findViewById(R.id.btnNext);

    if(savedInstanceState != null){
      titleInput.setText(savedInstanceState.getString(KEY_TITLE));
      authorInput.setText(savedInstanceState.getString(KEY_AUTHOR));
      yearInput.setText(savedInstanceState.getString(KEY_YEAR));
    }

    btnNext.setOnClickListener(v -> {
      String title = titleInput.getText().toString().trim();
      String author = authorInput.getText().toString().trim();
      String yearStr = yearInput.getText().toString().trim();

      if(title.isEmpty() || author.isEmpty() || yearStr.isEmpty()){
        Toast.makeText(this, R.string.error_missing_fields, Toast.LENGTH_SHORT).show();
        return;
      }

      int year = Integer.parseInt(yearStr);

      Book book = new Book(title, author, year);

      Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);

      intent.putExtra(EXTRA_BOOK, book);
      startActivity(intent); // pass the book details to the next activity
    });

    // Restore state if activity was recreated
    if (savedInstanceState != null) {
      titleInput.setText(savedInstanceState.getString(KEY_TITLE));
      authorInput.setText(savedInstanceState.getString(KEY_AUTHOR));
      yearInput.setText(savedInstanceState.getString(KEY_YEAR));
    }
  }


  @Override
  protected void onResume() {
    super.onResume();
    Log.d("lifecycle", "Main Activity - on Resume");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d("lifecycle", "Main Activity - on Destroy");
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.d("lifecycle", "Main Activity - on Pause");
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    Log.d("lifecycle", "Main Activity - in onSaveInstanceState");
    outState.putString(KEY_TITLE, titleInput.getText().toString());
    outState.putString(KEY_AUTHOR, authorInput.getText().toString());
    outState.putString(KEY_YEAR, yearInput.getText().toString());
  }
}