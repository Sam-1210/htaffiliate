package org.shopnow.utility;

import org.shopnow.structures.ApplicationProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataManager {
    private final String googleSpreadsheetID = "18L4taQ7YeY5TaT_jZGxHshber0ZJtbn5vGByZU1SVj0";
    private final String excelName = "";
    private final String SheetName;
    private List<List<Object>> table;
    Map<String, String> testcasesData= new HashMap<>();

    public TestDataManager(Class<?> TestClass) {
        SheetName = TestClass.getSimpleName();

        try{
            if(ApplicationProperties.getInstance().isGoogleSheet())
            {
                table = GoogleSheetUtil.getInstance().readSheet(googleSpreadsheetID, SheetName + "!A3:L");
            } else {
                table = ExcelUtils.readSheet(excelName, SheetName);
            }

            for(int i = 2; i < table.size(); i++) {
                testcasesData.put(table.get(i).get(9).toString(), table.get(i).get(4).toString());
            }
        } catch (Exception e) {
            Logger.Except(e);
        }
    }

    public String GetDataForTestCase(String TestID) {
        return testcasesData.get(TestID);
    }

    public void WriteResults(Map<String, String> testResults) {
        for(int i = 0; i < table.size(); i++) {
            String testResult = testResults.get(table.get(i).get(9).toString());
            if(testResult != null) {
                if(table.get(i).size() <= 11) table.get(i).add(testResult);
                else table.get(i).set(11, testResult);
            }
        }

        try {
            GoogleSheetUtil.getInstance().updateSheet(googleSpreadsheetID, SheetName+"!A3:L", table);
        } catch (Exception e) {
            Logger.Except(e);
        }
    }
}
