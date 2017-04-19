package mindful.app.release;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mindful.app.release.R;

public class AddQuestionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        setContentView(R.layout.activity_add_question);

        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean can_add_question = true;
                String insert_question = ((EditText) findViewById(R.id.insert_question)).getText().toString();
                String insert_negative_value = ((EditText) findViewById(R.id.insert_negative_value)).getText().toString();
                String insert_neutral_value = ((EditText) findViewById(R.id.insert_neutral_value)).getText().toString();
                String insert_positive_value = ((EditText) findViewById(R.id.insert_positive_value)).getText().toString();
                String insert_spinner_val = ((EditText) findViewById(R.id.question_id_word)).getText().toString();

                // if insert_question is default value or empty, don't add
                if (insert_question.isEmpty() || insert_question == null) {
                    can_add_question = false;
                    ((TextView) findViewById(R.id.error_message)).setText("Please insert valid question text (non default, non empty).");
                }   // if insert_negative_value is default value or empty, don't add
                if (can_add_question && (insert_negative_value.isEmpty() || insert_negative_value == null)) {
                    can_add_question = false;
                    ((TextView) findViewById(R.id.error_message)).setText("Please insert valid negative value (non default, non empty).");
                }   // if insert_neutral_value is default value or empty, don't add
                if (can_add_question && (insert_neutral_value.isEmpty() || insert_neutral_value == null)) {
                    can_add_question = false;
                    ((TextView) findViewById(R.id.error_message)).setText("Please insert valid neutral value (non default, non empty).");
                }   // if insert_positive_value is default value or empty, don't add
                if (can_add_question && (insert_positive_value.isEmpty() || insert_positive_value == null)) {
                    can_add_question = false;
                    ((TextView) findViewById(R.id.error_message)).setText("Please insert valid positive value (non default, non empty).");
                }   // if insert_spinner_value is default value or empty, don't add
                if (can_add_question && (insert_spinner_val.isEmpty() || insert_spinner_val == null)) {
                    can_add_question = false;
                    ((TextView) findViewById(R.id.error_message)).setText("Please insert valid identifying word for this question (non default, non empty).");
                }
                if (can_add_question) { // if can still add question, attempt to add question into database
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    // if failed question insertion
                    if (!db.addQuestion(new Question(db.get_max_question_id() + 1, insert_question, insert_negative_value, insert_neutral_value, insert_positive_value, insert_spinner_val))) {
                        ((TextView) findViewById(R.id.error_message)).setText("This question already exists in the database.");
                    } else {    // successful insertion, remove any potential error message and reset default values for edit_texts
                        ((TextView) findViewById(R.id.error_message)).setText("");
                        ((EditText) findViewById(R.id.insert_question)).setText("");
                        ((EditText) findViewById(R.id.insert_negative_value)).setText("");
                        ((EditText) findViewById(R.id.insert_neutral_value)).setText("");
                        ((EditText) findViewById(R.id.insert_positive_value)).setText("");
                        ((EditText) findViewById(R.id.question_id_word)).setText("");
                        Toast.makeText(getApplicationContext(), "Question added!", Toast.LENGTH_SHORT).show();
                    }
//                    else {    // successful question insertion
//                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//                        try {
//                            DocumentBuilder builder = factory.newDocumentBuilder();
//                            Document doc = builder.parse(openFileInput("/res/values/arrays.xml"));
//                            NodeList nodeslist = doc.getEle
//                        } catch(ParserConfigurationException e) {
//                            Log.e("mindful", "parserconfigurationexception", e);
//                        } catch(FileNotFoundException e) {
//                            Log.e("mindful", "filenotfoundexception", e);
//                        } catch(IOException e) {
//                            Log.e("mindful", "ioexception", e);
//                        } catch(SAXException e) {
//                            Log.e("mindful", "saxexception", e);
//                        }
//                    }
                }
            }
        });
    }
}
