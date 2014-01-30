package exp.viswanath.encryption;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener{

	private EditText inputBox;
	private EditText encryptedBox;
	private EditText decryptedBox;

	private Button encrypt;

	private SecurityProvider provider;

	private String data;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		inputBox = (EditText)findViewById(R.id.input);
		encryptedBox = (EditText)findViewById(R.id.encrypted);
		decryptedBox = (EditText)findViewById(R.id.decrypted);

		encrypt = (Button)findViewById(R.id.encrypt);
		encrypt.setOnClickListener(this);

		provider = SecurityProvider.getInstance();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.encrypt:
		{
			data = inputBox.getText().toString();
			
			try {

				String encryptedText = provider.encrypt(data);
				
				// Display the encrypted data
				encryptedBox.setText(encryptedText);

				// Display the decrypted data
				decryptedBox.setText(provider.decrypt(encryptedText));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		}
		default:
			break;
		}

	}

}
