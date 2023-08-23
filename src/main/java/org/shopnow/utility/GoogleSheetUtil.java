package org.shopnow.utility;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.stream.Collectors;


public class GoogleSheetUtil {

    private static final String APPLICATION_NAME = "HT AFFILIATE GAVALIDATION";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final Sheets sheet;
    private static final List<String> SCOPE = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_NAME = "GoogleOAuthCredentials.json";
    private static final GoogleSheetUtil instance = new GoogleSheetUtil();
    /**
     * HTTP_TRANSPORT The network HTTP Transport.
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final NetHttpTransport HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            sheet = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Unable to create connection with the Google Sheet network HTTP Transport : ", e);
        }
    }

    private GoogleSheetUtil() {
    }

    public static GoogleSheetUtil getInstance() {
        return instance;
    }

    /**
     * Creates an authorized Credential object.
     *
     * @return An authorized Credential object.
     * @throws IOException If the GoogleSheetCredentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = ClassLoader.getSystemResourceAsStream(CREDENTIALS_FILE_NAME);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_NAME);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPE)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }



    public static <T> List<Map<String, T>> convertListToMap(int keyRowIndex, List<List<Object>> tabularList, Class<T> clazz) {
        Objects.requireNonNull(tabularList, "tabularList must not be null!!!");
        Assert.assertTrue((keyRowIndex >= 0 && keyRowIndex < tabularList.size()), "keyRowIndex value must be positive and in range of tabularList");
        List<String> keys = tabularList.get(keyRowIndex).stream().map(String::valueOf).collect(Collectors.toList());
        List<Map<String, T>> dataMapList = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < tabularList.size(); rowIndex++) {
            if (rowIndex == keyRowIndex)
                continue;
            Map<String, T> dataMap = new HashMap<>();
            int colIndex = 0;
            for (String key : keys)
                if (colIndex < tabularList.get(rowIndex).size())
                    dataMap.put(key.trim(), convertDataType(tabularList.get(rowIndex).get(colIndex++), clazz));
                else
                    dataMap.put(key.trim(), convertDataType("", clazz));
            if (!dataMap.isEmpty())
                dataMapList.add(dataMap);
        }
        return dataMapList;
    }

    private static <T> T convertDataType(Object data, Class<T> clazz) {
        try {
            if(clazz.getName().equalsIgnoreCase("java.lang.Object"))
                return clazz.cast(data);
            return data == null ? null : clazz.getConstructor(String.class).newInstance(data.toString());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Unable to convert data [" + data + "] to provided data type [" + clazz.getName() + "]", e);
        }
    }

    /**
     * @param columnIndex - index starts with 1
     * @return Character of the provided column index as String
     */
    public static String getCharactersOfColumnIndex(int columnIndex) {
        StringBuilder chars = new StringBuilder();
        int leftIndexes = columnIndex % 26;
        if (columnIndex < 1)
            throw new RuntimeException("Column Index cannot be less than 1");
        if (columnIndex > 26) {
            int counter = columnIndex / 26;
            if (leftIndexes == 0) {
                counter--;
            }
            for (; counter > 0; counter--) {
                if (chars.length() == 0)
                    chars.append("A");
                else {
                    char lastChar = chars.charAt(chars.length() - 1);
                    if (((int) lastChar) > 90)
                        chars.replace(chars.length() - 2, chars.length() - 1, "ZA");
                    else
                        chars.replace(chars.length() - 1, chars.length(), "" + ((char) (((int) lastChar) + 1)));
                }
            }
        }
        if (leftIndexes == 0 && columnIndex > 25)
            leftIndexes = 26;
        if (leftIndexes > 0)
            chars.append(((char) (64 + leftIndexes)));
        return chars.toString();
    }

    public List<List<Object>> readSheet(String spreadsheetId, String sheetRange) throws IOException {
        return sheet
                .spreadsheets()
                .values()
                .get(spreadsheetId, sheetRange)
                .execute()
                .getValues();
    }

    /**
     * @param spreadsheetId - Sheet ID i.e. 1MUfmFe-_a3hLxYjPp54bdBJgfA3viS9IJ42o6Jb-W3E
     * @param sheetRange    - SheetRange could be sheet name as well as sheet name + Raw and Col i.e. Sheet0 OR Sheet0!A1:A6
     * @param content       - data to be inserted in sheet
     * @return Updated Values Response
     * @throws IOException If unable to update content in sheet
     */
    public UpdateValuesResponse updateSheet(String spreadsheetId, String sheetRange, List<List<Object>> content) throws IOException {
        ValueRange valueRange = new ValueRange()
                .setValues(content);
        return sheet
                .spreadsheets()
                .values()
                .update(spreadsheetId, sheetRange, valueRange)
                .setValueInputOption("RAW")
                .setIncludeValuesInResponse(true)
                .execute();
    }

    /**
     * @param spreadsheetId - Sheet ID i.e. 1MUfmFe-_a3hLxYjPp54bdBJgfA3viS9IJ42o6Jb-W3E
     * @param contentMap    - data to be inserted in multiple defined range in sheet
     *                      i.e. map.put(range, content)
     *                      e.g.
     *                      contentMap.put("D1", Arrays.asList(
     *                      Arrays.asList("January Total", "=B2+B3")));
     *                      contentMap.put("D4", Arrays.asList(
     *                      Arrays.asList("February Total", "=B5+B6")));
     * @return Updated Values Response
     * @throws IOException If unable to update content in sheet
     */
    public BatchUpdateValuesResponse batchUpdateSheet(String spreadsheetId, Map<String, List<List<Object>>> contentMap) throws IOException {
        List<ValueRange> valueRangeList = new ArrayList<>();
        for (Map.Entry<String, List<List<Object>>> contentMapEntry : contentMap.entrySet())
            valueRangeList.add(new ValueRange().setRange(contentMapEntry.getKey()).setValues(contentMapEntry.getValue()));
        BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
                .setValueInputOption("USER_ENTERED")
                .setData(valueRangeList);
        return sheet
                .spreadsheets()
                .values()
                .batchUpdate(spreadsheetId, batchBody)
                .execute();
    }

    public int getSheetId(String spreadsheetId, String sheetName) throws IOException {
        Spreadsheet spreadsheet = sheet.spreadsheets().get(spreadsheetId).setIncludeGridData(false).execute();
        for (Sheet sheet : spreadsheet.getSheets()) {
            SheetProperties sheetProperties = sheet.getProperties();
            if (sheetProperties.getTitle().equals(sheetName))
                return sheetProperties.getSheetId();
        }
        return -1;
    }

    public void insertRow(String spreadsheetId, int sheetId, int startIndex, int endIndex) throws IOException {
        InsertDimensionRequest insertRow = new InsertDimensionRequest();
        insertRow.setRange(new DimensionRange().setDimension("ROWS").setStartIndex(startIndex).setEndIndex(endIndex).setSheetId(sheetId))
                .setInheritFromBefore(true);
        BatchUpdateSpreadsheetRequest r = new BatchUpdateSpreadsheetRequest().setRequests(Collections.singletonList(
                new Request().setInsertDimension(insertRow)
        ));
        sheet.spreadsheets().batchUpdate(spreadsheetId, r).execute();
    }

    public void insertCol(String spreadsheetId, int sheetId, int startIndex, int endIndex) throws IOException {
        InsertDimensionRequest insertCol = new InsertDimensionRequest();
        insertCol.setRange(new DimensionRange().setDimension("COLUMNS").setStartIndex(startIndex).setEndIndex(endIndex).setSheetId(sheetId))
                .setInheritFromBefore(true);
        BatchUpdateSpreadsheetRequest r = new BatchUpdateSpreadsheetRequest().setRequests(Collections.singletonList(
                new Request().setInsertDimension(insertCol)
        ));
        sheet.spreadsheets().batchUpdate(spreadsheetId, r).execute();
    }

    public void batchUpdateSheet(String spreadsheetId, String sheetRange, List<List<Object>> data) throws IOException {
        List<ValueRange> valueRangeList = new ArrayList<>();
        valueRangeList.add(new ValueRange().setRange(sheetRange).setValues(data));
        BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
                .setValueInputOption("USER_ENTERED")
                .setData(valueRangeList);
        sheet
                .spreadsheets()
                .values()
                .batchUpdate(spreadsheetId, batchBody)
                .execute();
    }

    public void batchUpdateSheet(String spreadsheetId, BatchUpdateSpreadsheetRequest batchRequest) throws IOException {
        sheet
                .spreadsheets()
                .batchUpdate(spreadsheetId, batchRequest)
                .execute();
    }

    /**
     * @param spreadsheetId - Sheet ID i.e. 1MUfmFe-_a3hLxYjPp54bdBJgfA3viS9IJ42o6Jb-W3E
     * @param sheetRange    - Sheet Name + Starting Point i.e. Sheet0!A1
     * @param content       - data to be appended at the end of the existing rows in sheet
     * @return Append Values Response
     * @throws IOException If unable to update content in sheet
     */
    public AppendValuesResponse appendDataToSheet(String spreadsheetId, String sheetRange, List<List<Object>> content) throws IOException {
        ValueRange valueRange = new ValueRange()
                .setValues(content);
        return sheet
                .spreadsheets()
                .values()
                .append(spreadsheetId, sheetRange, valueRange)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();
    }

    public Sheets getSheetService() {
        return sheet;
    }

    public <T> List<Map<String, T>> readSheetAsListOfMap(String spreadsheetId, String sheetRange, Class<T> type) throws IOException {
        return convertListToMap(0, readSheet(spreadsheetId, sheetRange), type);
    }

    public void terminateConnection() throws IOException {
        HTTP_TRANSPORT.shutdown();
    }

    public static void main(String[] args) {
        try {
            GoogleSheetUtil.getInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}