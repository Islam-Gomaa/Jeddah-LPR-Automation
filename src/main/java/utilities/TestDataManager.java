package utils;

public final class TestDataManager {

    private static final String RUN_ID_PROPERTY =
            "test.data.run.id";

    private static final String PERSISTENCE_PROPERTY =
            "test.data.persistence";

    private static final String DEFAULT_RUN_ID =
            "default";

    private TestDataManager() {
        // Utility class
    }

    // =========================================================
    // English
    // =========================================================

    public static String generateEnglish(
            String key,
            String baseValue) {

        validateKey(key);

        String storageKey =
                buildKey(key);

        String existingValue =
                getStoredValue(storageKey);

        if (existingValue != null) {
            return existingValue;
        }

        String generatedValue =
                TestDataGenerator.generateEnglish(baseValue);

        saveStoredValue(
                storageKey,
                generatedValue
        );

        return generatedValue;
    }

    // =========================================================
    // Arabic
    // =========================================================

    public static String generateArabic(
            String key,
            String baseValue) {

        validateKey(key);

        String storageKey =
                buildKey(key);

        String existingValue =
                getStoredValue(storageKey);

        if (existingValue != null) {
            return existingValue;
        }

        String generatedValue =
                TestDataGenerator.generateArabic(baseValue);

        saveStoredValue(
                storageKey,
                generatedValue
        );

        return generatedValue;
    }

    // =========================================================
    // Get
    // =========================================================

    public static String get(String key) {

        validateKey(key);

        return getStoredValue(
                buildKey(key)
        );
    }

    // =========================================================
    // Update
    // =========================================================

    public static void update(
            String key,
            String value) {

        validateKey(key);
        validateValue(value);

        saveStoredValue(
                buildKey(key),
                value
        );
    }

    // =========================================================
    // Remove
    // =========================================================

    public static void remove(String key) {

        validateKey(key);

        if (isPersistenceEnabled()) {

            TestDataStore.remove(
                    buildKey(key)
            );
        }
    }

    // =========================================================
    // Clear Current Run
    // =========================================================

    public static void clearCurrentRun() {

        if (!isPersistenceEnabled()) {
            return;
        }

        TestDataStore.clear();
    }

    // =========================================================
    // Prepare Data Model
    // =========================================================

    public static void prepareDataModel(
            data.DataModel dataModel) {

        if (dataModel == null) {
            throw new IllegalArgumentException(
                    "DataModel cannot be null"
            );
        }

        prepareAdvertisements(dataModel);
        prepareBlogCategories(dataModel);
        prepareBlogs(dataModel);
        prepareCategory(dataModel);
        prepareClients(dataModel);
        prepareFeatureGroup(dataModel);
        preparePartners(dataModel);
        prepareProducts(dataModel);
        prepareUseCases(dataModel);
        prepareUsers(dataModel);
        prepareRoles(dataModel);
    }

    // =========================================================
    // Advertisements
    // =========================================================

    private static void prepareAdvertisements(
            data.DataModel dataModel) {

        if (dataModel.Advertisements == null) {
            return;
        }

        dataModel.Advertisements.titleAr =
                generateArabic(
                        "Advertisements.titleAr",
                        dataModel.Advertisements.titleAr
                );

        dataModel.Advertisements.titleEn =
                generateEnglish(
                        "Advertisements.titleEn",
                        dataModel.Advertisements.titleEn
                );

        dataModel.Advertisements.editTitleAr =
                generateArabic(
                        "Advertisements.editTitleAr",
                        dataModel.Advertisements.editTitleAr
                );

        dataModel.Advertisements.editTitleEn =
                generateEnglish(
                        "Advertisements.editTitleEn",
                        dataModel.Advertisements.editTitleEn
                );
    }

    // =========================================================
    // Blog Categories
    // =========================================================

    private static void prepareBlogCategories(
            data.DataModel dataModel) {

        if (dataModel.BlogCategories == null) {
            return;
        }

        dataModel.BlogCategories.titleArabic =
                generateArabic(
                        "BlogCategories.titleArabic",
                        dataModel.BlogCategories.titleArabic
                );

        dataModel.BlogCategories.titleEnglish =
                generateEnglish(
                        "BlogCategories.titleEnglish",
                        dataModel.BlogCategories.titleEnglish
                );

        dataModel.BlogCategories.editTitleArabic =
                generateArabic(
                        "BlogCategories.editTitleArabic",
                        dataModel.BlogCategories.editTitleArabic
                );

        dataModel.BlogCategories.editTitleEnglish =
                generateEnglish(
                        "BlogCategories.editTitleEnglish",
                        dataModel.BlogCategories.editTitleEnglish
                );
    }

    // =========================================================
    // Blogs
    // =========================================================

    private static void prepareBlogs(
            data.DataModel dataModel) {

        if (dataModel.Blogs == null) {
            return;
        }

        dataModel.Blogs.titleArabic =
                generateArabic(
                        "Blogs.titleArabic",
                        dataModel.Blogs.titleArabic
                );

        dataModel.Blogs.titleEnglish =
                generateEnglish(
                        "Blogs.titleEnglish",
                        dataModel.Blogs.titleEnglish
                );

        dataModel.Blogs.editTitleArabic =
                generateArabic(
                        "Blogs.editTitleArabic",
                        dataModel.Blogs.editTitleArabic
                );

        dataModel.Blogs.editTitleEnglish =
                generateEnglish(
                        "Blogs.editTitleEnglish",
                        dataModel.Blogs.editTitleEnglish
                );
    }

    // =========================================================
    // Category
    // =========================================================

    private static void prepareCategory(
            data.DataModel dataModel) {

        if (dataModel.Category == null) {
            return;
        }

        dataModel.Category.titleAr =
                generateArabic(
                        "Category.titleAr",
                        dataModel.Category.titleAr
                );

        dataModel.Category.titleEn =
                generateEnglish(
                        "Category.titleEn",
                        dataModel.Category.titleEn
                );

        dataModel.Category.editTitleAr =
                generateArabic(
                        "Category.editTitleAr",
                        dataModel.Category.editTitleAr
                );

        dataModel.Category.editTitleEn =
                generateEnglish(
                        "Category.editTitleEn",
                        dataModel.Category.editTitleEn
                );
    }

    // =========================================================
    // Clients
    // =========================================================

    private static void prepareClients(
            data.DataModel dataModel) {

        if (dataModel.Clients == null) {
            return;
        }

        dataModel.Clients.nameAR =
                generateArabic(
                        "Clients.nameAR",
                        dataModel.Clients.nameAR
                );

        dataModel.Clients.nameEN =
                generateEnglish(
                        "Clients.nameEN",
                        dataModel.Clients.nameEN
                );

        dataModel.Clients.editNameAR =
                generateArabic(
                        "Clients.editNameAR",
                        dataModel.Clients.editNameAR
                );

        dataModel.Clients.editNameEN =
                generateEnglish(
                        "Clients.editNameEN",
                        dataModel.Clients.editNameEN
                );
    }

    // =========================================================
    // Feature Group
    // =========================================================

    private static void prepareFeatureGroup(
            data.DataModel dataModel) {

        if (dataModel.FeatureGroup == null) {
            return;
        }

        dataModel.FeatureGroup.nameAR =
                generateArabic(
                        "FeatureGroup.nameAR",
                        dataModel.FeatureGroup.nameAR
                );

        dataModel.FeatureGroup.nameEN =
                generateEnglish(
                        "FeatureGroup.nameEN",
                        dataModel.FeatureGroup.nameEN
                );

        dataModel.FeatureGroup.editNameAR =
                generateArabic(
                        "FeatureGroup.editNameAR",
                        dataModel.FeatureGroup.editNameAR
                );

        dataModel.FeatureGroup.editNameEN =
                generateEnglish(
                        "FeatureGroup.editNameEN",
                        dataModel.FeatureGroup.editNameEN
                );
    }

    // =========================================================
    // Partners
    // =========================================================

    private static void preparePartners(
            data.DataModel dataModel) {

        if (dataModel.Partners == null) {
            return;
        }

        dataModel.Partners.nameAR =
                generateArabic(
                        "Partners.nameAR",
                        dataModel.Partners.nameAR
                );

        dataModel.Partners.nameEN =
                generateEnglish(
                        "Partners.nameEN",
                        dataModel.Partners.nameEN
                );

        dataModel.Partners.editNameAR =
                generateArabic(
                        "Partners.editNameAR",
                        dataModel.Partners.editNameAR
                );

        dataModel.Partners.editNameEN =
                generateEnglish(
                        "Partners.editNameEN",
                        dataModel.Partners.editNameEN
                );
    }

    // =========================================================
    // Products
    // =========================================================

    private static void prepareProducts(
            data.DataModel dataModel) {

        if (dataModel.Products == null) {
            return;
        }

        dataModel.Products.titleArabic =
                generateArabic(
                        "Products.titleArabic",
                        dataModel.Products.titleArabic
                );

        dataModel.Products.titleEnglish =
                generateEnglish(
                        "Products.titleEnglish",
                        dataModel.Products.titleEnglish
                );

        dataModel.Products.editTitleArabic =
                generateArabic(
                        "Products.editTitleArabic",
                        dataModel.Products.editTitleArabic
                );

        dataModel.Products.editTitleEnglish =
                generateEnglish(
                        "Products.editTitleEnglish",
                        dataModel.Products.editTitleEnglish
                );
    }

    // =========================================================
    // Use Cases
    // =========================================================

    private static void prepareUseCases(
            data.DataModel dataModel) {

        if (dataModel.UseCases == null) {
            return;
        }

        dataModel.UseCases.titleAR =
                generateArabic(
                        "UseCases.titleAR",
                        dataModel.UseCases.titleAR
                );

        dataModel.UseCases.titleEN =
                generateEnglish(
                        "UseCases.titleEN",
                        dataModel.UseCases.titleEN
                );

        dataModel.UseCases.editTitleAR =
                generateArabic(
                        "UseCases.editTitleAR",
                        dataModel.UseCases.editTitleAR
                );

        dataModel.UseCases.editTitleEN =
                generateEnglish(
                        "UseCases.editTitleEN",
                        dataModel.UseCases.editTitleEN
                );
    }

    // =========================================================
    // Users
    // =========================================================

    private static void prepareUsers(
            data.DataModel dataModel) {

        if (dataModel.Users == null) {
            return;
        }

        dataModel.Users.name =
                generateEnglish(
                        "Users.name",
                        dataModel.Users.name
                );

        dataModel.Users.editName =
                generateEnglish(
                        "Users.editName",
                        dataModel.Users.editName
                );
    }

    // =========================================================
    // Roles
    // =========================================================

    private static void prepareRoles(
            data.DataModel dataModel) {

        if (dataModel.Roles == null) {
            return;
        }

        dataModel.Roles.roleName =
                generateEnglish(
                        "Roles.roleName",
                        dataModel.Roles.roleName
                );

        dataModel.Roles.displayNameAr =
                generateArabic(
                        "Roles.displayNameAr",
                        dataModel.Roles.displayNameAr
                );

        dataModel.Roles.displayNameEn =
                generateEnglish(
                        "Roles.displayNameEn",
                        dataModel.Roles.displayNameEn
                );

        dataModel.Roles.editRoleName =
                generateEnglish(
                        "Roles.editRoleName",
                        dataModel.Roles.editRoleName
                );

        dataModel.Roles.editDisplayNameAr =
                generateArabic(
                        "Roles.editDisplayNameAr",
                        dataModel.Roles.editDisplayNameAr
                );

        dataModel.Roles.editDisplayNameEn =
                generateEnglish(
                        "Roles.editDisplayNameEn",
                        dataModel.Roles.editDisplayNameEn
                );
    }

    // =========================================================
    // Internal Storage
    // =========================================================

    private static String getStoredValue(
            String storageKey) {

        if (!isPersistenceEnabled()) {
            return null;
        }

        return TestDataStore.get(storageKey);
    }

    private static void saveStoredValue(
            String storageKey,
            String value) {

        if (!isPersistenceEnabled()) {
            return;
        }

        TestDataStore.set(
                storageKey,
                value
        );
    }

    // =========================================================
    // Configuration
    // =========================================================

    private static boolean isPersistenceEnabled() {

        return Boolean.parseBoolean(
                System.getProperty(
                        PERSISTENCE_PROPERTY,
                        "false"
                )
        );
    }

    private static String getRunId() {

        return System.getProperty(
                RUN_ID_PROPERTY,
                DEFAULT_RUN_ID
        );
    }

    private static String buildKey(
            String key) {

        validateKey(key);

        return getRunId() + "." + key;
    }

    // =========================================================
    // Validation
    // =========================================================

    private static void validateKey(
            String key) {

        if (key == null || key.isBlank()) {

            throw new IllegalArgumentException(
                    "Test data key cannot be null or empty"
            );
        }
    }

    private static void validateValue(
            String value) {

        if (value == null || value.isBlank()) {

            throw new IllegalArgumentException(
                    "Test data value cannot be null or empty"
            );
        }
    }
}