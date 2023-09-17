package org.shopnow.utility;

import org.shopnow.structures.ApplicationProperties;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataManager {
    private final String googleSpreadsheetID = "18L4taQ7YeY5TaT_jZGxHshber0ZJtbn5vGByZU1SVj0";
    private final String excelName = "";
    private final String SheetName;
    private List<List<Object>> SheetData;
    Map<String, String> testcasesData= new HashMap<>();
    private final int TESTDATA_COLUMN = 4; // 0 based
    private final int TESGNGMETHOD_COLUMN = 9; // 0 based
    private final int TEST_RESULT_COLUMN = 10; // 0 based
    private final int TESTCASE_FIRST_ROW = 2; // 0 based
    private final char LAST_COLUMN = 'L';
    private final String TEST_CASE_RANGE;
    private final int META_ROW = 0;
    private final int TIME_COLUMN = 5;
    private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public TestDataManager(Class<?> TestClass) {
        SheetName = TestClass.getSimpleName();
        TEST_CASE_RANGE = String.format("%s!A%d:%c", SheetName, 1, LAST_COLUMN);
        try{
            if(ApplicationProperties.getInstance().isGoogleSheet())
            {
                SheetData = GoogleSheetUtil.getInstance().readSheet(googleSpreadsheetID, TEST_CASE_RANGE);
            } else {
                SheetData = ExcelUtils.readSheet(excelName, SheetName);
            }

            for(int i = TESTCASE_FIRST_ROW; i < SheetData.size(); i++) {
                testcasesData.put(SheetData.get(i).get(TESGNGMETHOD_COLUMN).toString(), SheetData.get(i).get(TESTDATA_COLUMN).toString());
            }
        } catch (Exception e) {
            Logger.Except(e);
        }
    }

    public String GetDataForTestCase(String TestNGMethodName) {
        return testcasesData.get(TestNGMethodName);
    }

    public void WriteResults(Map<String, String> testResults) {
        String timestamp = timestampFormat.format(new Date());
        if(SheetData.get(META_ROW).size() >= TIME_COLUMN + 1) {
            SheetData.get(META_ROW).set(TIME_COLUMN, timestamp);
        }
        else {
            while(SheetData.get(META_ROW).size() < TIME_COLUMN) SheetData.get(META_ROW).add("");
            SheetData.get(META_ROW).add(timestamp);
        }

        for(int i = TESTCASE_FIRST_ROW; i < SheetData.size(); i++) {
            String testResult = testResults.get(SheetData.get(i).get(TESGNGMETHOD_COLUMN).toString());
            if(testResult != null) {
                if(SheetData.get(i).size() >= TEST_RESULT_COLUMN + 1) {
                    SheetData.get(i).set(TEST_RESULT_COLUMN, testResult);
                }
                else {
                    while(SheetData.get(i).size() < TEST_RESULT_COLUMN) SheetData.get(i).add("");
                    SheetData.get(i).add(testResult);
                }
            }
        }

        try {
            GoogleSheetUtil.getInstance().updateSheet(googleSpreadsheetID, TEST_CASE_RANGE, SheetData);
        } catch (Exception e) {
            Logger.Except(e);
        }
    }
}
