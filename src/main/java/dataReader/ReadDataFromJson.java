package dataReader;

import com.google.gson.Gson;
import data.DataModel;
import utilities.ConfigReader;
import utilities.TestDataManager;

import java.io.FileReader;

public class ReadDataFromJson {

    private static DataModel data;

    public DataModel readJsonFile() {

        try {

            String env =
                    ConfigReader.get("environment");

            String filePath =
                    "src/test/resources/TestData/"
                            + env
                            + "/testData.json";

            try (FileReader fileReader =
                         new FileReader(filePath)) {

                DataModel dataModel =
                        new Gson().fromJson(
                                fileReader,
                                DataModel.class
                        );

                TestDataManager.prepareDataModel(
                        dataModel
                );

                return dataModel;
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to read test data JSON file",
                    e
            );
        }
    }

    public static DataModel dataModel() {

        if (data == null) {

            data =
                    new ReadDataFromJson()
                            .readJsonFile();
        }

        return data;
    }
}