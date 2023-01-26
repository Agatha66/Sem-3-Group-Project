package wcdonaldapplication;

//to get access to use spreadsheets
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

public class SpreadSheets {
	static Sheets sheetsService;
	private static String APPLICATION_NAME = "Google Sheets";
	static String SPREADSHEET_ID = "1ocXAY0btdCs1_QfnAS_ZaTh13hXigydE1rKAnvAvybI";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	
	public static String getSPREADSHEET_ID() {
		return SPREADSHEET_ID;
	}
	
	public static void setSheetsService(Sheets sheetsService) {
		SpreadSheets.sheetsService = sheetsService;
	}

	private static Credential authorize() throws IOException, GeneralSecurityException{
		InputStream in = Main.class.getResourceAsStream("/credentials.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		
		List<String> scopes =  Arrays.asList(SheetsScopes.SPREADSHEETS);
		
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, scopes)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
				.setAccessType("offline")
				.build();
		Credential credential = new AuthorizationCodeInstalledApp(
				flow, new LocalServerReceiver())
				.authorize("user");
		return credential;			
	}
	
	public static Sheets getSheetsService() throws IOException, GeneralSecurityException{
		Credential credential = authorize();
		return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
				JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME)
				.build();
	}
}
