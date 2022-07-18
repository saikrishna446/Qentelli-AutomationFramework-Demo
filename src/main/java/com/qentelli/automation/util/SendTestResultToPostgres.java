package com.qentelli.automation.util;

import com.qentelli.automation.util.DBResult.RootModel;
import com.qentelli.automation.utilities.CommonUtilities;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
//import com.qentelli.automation.util.DBResult.RootModel;
//import com.qentelli.automation.util.DBResult.Scenario;
//import com.qentelli.automation.util.DBResult.Step;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SendTestResultToPostgres {

    private static final Logger log = LogManager.getLogger(SendTestResultToPostgres.class);

    public static void send(String endPointAction, String jsonDataSentToPostgreSQL) {
        try {
            // Alternative RPC to send the same data to db.
            //URL url = new URL("http://automation.test.com:4999/insert");
            // Automation server endpoint:
            URL url = null;
            switch (endPointAction) {
                case "insert":
                    // url = new URL("http://localhost:4999/insert");
                    // dev service port
                    // url = new URL("http://automation.test.com:4995/insert");

                    // Live service port
                    url = new URL("http://localhost:5432/postgres/insert");
                    break;
                case "updateSet":
                    // url = new URL("http://localhost:4999/updateSet");
                    // Dev service port
                    // url = new URL("http://automation.test.com:4995/updateSet");
                    // Live service port
                    url = new URL("http://localhost:5432/updateSet");
                    break;
            }

//            log.info("Postgres Service url = " + url);
//            String auth = "postgres" + ":" + "superuser";
//            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
//            String authHeaderValue = "Basic " + new String(encodedAuth);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestProperty("Authorization", authHeaderValue);
            int responseCode = conn.getResponseCode();
            conn.setConnectTimeout(5000);
            switch (endPointAction) {
                case "insert":
                    conn.setRequestMethod("POST");
                    break;
                case "updateSet":
                    conn.setRequestMethod("PUT");
                    break;
            }
            System.out.println("conn.getRequestMethod() = " + conn.getRequestMethod());
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // Write the JSON to output stream for service
            OutputStream os = conn.getOutputStream();
            os.write(jsonDataSentToPostgreSQL.getBytes("utf-8"));
            os.flush();

            if (conn.getResponseCode() != 200) {
                log.info("Failed : HTTP Error code : " + conn.getResponseCode());
            } else {
                log.info("Passed : DB Insertion " + conn.getResponseCode());
            }


            conn.disconnect();
        } catch (Exception e) {
            log.warn("Failed to insert results into PostgreSQL db, message is =  " + e.getMessage());
        }
    }

    public static void send2(JSONObject jsonDataSentToPostgreSQL) {
         String url = "jdbc:postgresql://localhost:5432/postgres";
         String user = "postgres";
         String password = "superuser";
        int set_id=0;
        String status="Pass";
        try {
            DBResult.RootModel data = new Gson().fromJson(jsonDataSentToPostgreSQL.toString(), DBResult.RootModel.class);
            // DBResult.Scenario dataScenario = (DBResult.Scenario) data.scenario.get(0);
            //DBResult.Step dataStep = (DBResult.Step) data.step.get(0);
//            Object stepArray = jsonDataSentToPostgreSQL.get("step");
//            Object stepValue = ((JSONArray) stepArray).get(0);
//            Object scenarioArray = jsonDataSentToPostgreSQL.get("scneario");
//            Object scenarioValue = ((JSONArray) stepArray).get(0);


            DBResult.Scenario dataScenario = null;
            LinkedList<String> scenarios = new LinkedList<>();
            LinkedList<Integer> scenariIds = new LinkedList<>();
            int scenario_id = 0;
            //int set_id = Integer.parseInt(DateTimeFormatter.ofPattern("HHmmssSSS").format(LocalDateTime.now()));
             set_id = Integer.parseInt(System.getProperty("testId"));
            int project_id = 2;
            int locale_id = 2;
            int application_id = 1;
            int bucket_id = 1;
            int suite_id = 1;
            int env_id = 6;
            int run_id = Integer.parseInt(DateTimeFormatter.ofPattern("HHmmssSSS").format(LocalDateTime.now()));
            //int scenario_id=Integer.parseInt(DateTimeFormatter.ofPattern("HHmmssSSS").format(LocalDateTime.now()));

            String Insert_Set = "INSERT INTO public.set(\n" +
                    "\t set_id, project_id, locale_id, application_id, bucket_id, suite_id, env_id, run_id,testrail, loglink, lid, mobile, platform, browser, failed, skipped, passed, total, " +
                    "duration, start_time, end_time, \"time\", by_user)\n" +
                    "\t VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?);";

            if(data.failed>0){
                status="Fail";
            }
            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(Insert_Set)) {
                preparedStatement.setInt(1, set_id);
                preparedStatement.setInt(2, project_id);
                preparedStatement.setInt(3, locale_id);
                preparedStatement.setInt(4, application_id);
                preparedStatement.setInt(5, bucket_id);
                preparedStatement.setInt(6, suite_id);
                preparedStatement.setInt(7, env_id);
                preparedStatement.setInt(8, run_id);
                preparedStatement.setString(9, data.testRail);
                preparedStatement.setString(10, "");
                preparedStatement.setLong(11, data.lid);
                preparedStatement.setBoolean(12, data.mobile);
                preparedStatement.setString(13, data.platform);
                preparedStatement.setString(14, data.browser);
                preparedStatement.setInt(15, data.failed);
                preparedStatement.setInt(16, data.skipped);
                preparedStatement.setInt(17, data.passed);
                preparedStatement.setInt(18, data.total);
                preparedStatement.setInt(19, data.duration);
                preparedStatement.setLong(20, data.start);
                preparedStatement.setLong(21, data.end);
                preparedStatement.setLong(22, data.time);
                preparedStatement.setString(23, data.user);
                System.out.println(preparedStatement);
                // Step 3: Execute the query or update query
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                log.warn("Failed to insert results into PostgreSQL db, message is =  " + e.getMessage());
            }
            String Insert_Scenario = "INSERT INTO public.scenario(\n" +
                    "\t scenario_id, set_id, run_id,scenario_name, testrail, feature_name, error_type, lid, testraillink, duration, start_time, " +
                    "end_time, total_steps, result, failed, skipped, passed, sauce_link, server_info, sauce_video," +
                    " sauce_html, comment, locale_id)\n" +
                    "\tVALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?);";

            try (Connection connection2 = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement2 = connection2.prepareStatement(Insert_Scenario)) {
                for (int i = 0; i < data.scenario.size(); i++) {
                    dataScenario = (DBResult.Scenario) data.scenario.get(i);
                    String scenario_value = Long.toString(dataScenario.time).substring(8, 13);
                    scenario_id = Integer.parseInt(scenario_value);
                    preparedStatement2.setInt(1, scenario_id);
                    preparedStatement2.setInt(2, set_id);
                    preparedStatement2.setInt(3, run_id);
                    preparedStatement2.setString(4, dataScenario.scenarioName);
                    scenarios.add(dataScenario.scenarioName);
                    scenariIds.add(scenario_id);
                    preparedStatement2.setString(5, dataScenario.testRail);
                    preparedStatement2.setString(6, dataScenario.featureName);
                    preparedStatement2.setString(7, dataScenario.errorType);
                    preparedStatement2.setLong(8, dataScenario.lid);
                    preparedStatement2.setString(9, dataScenario.testRailLink);
                    preparedStatement2.setInt(10, dataScenario.duration);
                    preparedStatement2.setLong(11, dataScenario.start);
                    preparedStatement2.setLong(12, dataScenario.end);
                    preparedStatement2.setLong(13, dataScenario.totalSteps);
                    preparedStatement2.setString(14, dataScenario.result);
                    preparedStatement2.setInt(15, dataScenario.failed);
                    preparedStatement2.setInt(16, dataScenario.skipped);
                    preparedStatement2.setInt(17, dataScenario.passed);
                    preparedStatement2.setString(18, dataScenario.sauceLink);
                    preparedStatement2.setString(19, dataScenario.serverInfo);
                    preparedStatement2.setString(20, dataScenario.sauceVideo);
                    preparedStatement2.setString(21, dataScenario.sauceHtml);
                    preparedStatement2.setString(22, dataScenario.comment);
                    preparedStatement2.setInt(23, locale_id);
                    preparedStatement2.execute();
//                    System.out.println(preparedStatement2);
//                    preparedStatement2.addBatch();
                }
                // Step 3: Execute the query or update query
//                preparedStatement2.executeBatch();
            } catch (Exception e) {
                log.warn("Failed to insert results into PostgreSQL db, message is =  " + e.getMessage());
            }

            //get scenario names
            for (int i = 0; i < scenarios.size(); i++) {
                int finalI = i;

                List<DBResult.Step> scenario = data.step.stream().filter((s) -> s.scenarioName.equals(scenarios.get(finalI))).collect(Collectors.toList());
                // scenario = data.step.stream().map(p -> p.scenarioName.equals(data.scenario.get(finalI).scenarioName)).collect(Collectors.toList());
                //}
                String Insert_step = "INSERT INTO public.step(\n" +
                        "\t step_id,scenario_id, run_id,step_name, testrail, lid,duration, start_time, end_time, result,\"scenarioName\")\n" +
                        "\t VALUES (?,?,?, ?, ?, ?,?,?,?,?,?);";
                try (Connection connection2 = DriverManager.getConnection(url, user, password);
                     PreparedStatement preparedStatement3 = connection2.prepareStatement(Insert_step)) {
                    for (int j = 0; j < scenario.size(); j++) {
                        int step_id = Integer.parseInt(DateTimeFormatter.ofPattern("HHmmssSSS").format(LocalDateTime.now()));
                        int finalI2 = j;
                        preparedStatement3.setInt(1, step_id + CommonUtilities.getRandomNumber(10, 1000));
                        //  List<DBResult.Scenario> scen = data.scenario.stream().filter((s) -> s.scenarioName.equals(scenario.get(finalI2).scenarioName)).collect(Collectors.toList());
                        preparedStatement3.setInt(2, scenariIds.get(i));
                        preparedStatement3.setLong(3, run_id);
                        preparedStatement3.setString(4, scenario.get(j).step);
                        preparedStatement3.setString(5, scenario.get(j).testRail);
                        preparedStatement3.setLong(6, scenario.get(j).lid);
                        preparedStatement3.setLong(7, scenario.get(j).duration);
                        preparedStatement3.setLong(8, scenario.get(j).start);
                        preparedStatement3.setLong(9, scenario.get(j).end);
                        preparedStatement3.setString(10, scenario.get(j).result);
                        preparedStatement3.setString(11, scenario.get(j).scenarioName);
                        preparedStatement3.execute();
//                preparedStatement2.setLong(6, dataScenario.v);
//                        preparedStatement3.addBatch();
                    }

                    System.out.println(preparedStatement3);
                    // Step 3: Execute the query or update query
//                    preparedStatement3.executeBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                    log.warn("Failed to insert results into PostgreSQL db, message is =  " + e.getMessage());
                }

            }

        } catch (Exception e) {

        }finally {
            writeFinalStatus(url,user,password,status,set_id);
            insertHealingData(url,user,password,set_id);

        }
    }

    public static void sendToMySQL(JSONObject jsonDataSentToPostgreSQL) {
        String url = "jdbc:mysql://172.16.12.35:3306/Mobe_Dev_Local?characterEncoding=latin1&useConfigs=maxPerformance";
        String user = "mobeuser";
        String password = "M0B@KenT1i!2O@2";
        int set_id=0;
        String status="Pass";
        try {

            DBResult.RootModel data = new Gson().fromJson(jsonDataSentToPostgreSQL.toString(), DBResult.RootModel.class);
            // DBResult.Scenario dataScenario = (DBResult.Scenario) data.scenario.get(0);
            //DBResult.Step dataStep = (DBResult.Step) data.step.get(0);
            DBResult.Scenario dataScenario = null;
            LinkedList<String> scenarios = new LinkedList<>();
            LinkedList<Integer> scenariIds = new LinkedList<>();
            int scenario_id = 0;
             set_id = Integer.parseInt(System.getProperty("testId"));
            int project_id = 2;
            int locale_id = 2;
            int application_id = 1;
            int bucket_id = 1;
            int suite_id = 1;
            int env_id = 6;
            int run_id = Integer.parseInt(DateTimeFormatter.ofPattern("HHmmssSSS").format(LocalDateTime.now()));
            //int scenario_id=Integer.parseInt(DateTimeFormatter.ofPattern("HHmmssSSS").format(LocalDateTime.now()));
            String Insert_Set = "INSERT INTO `set`(\n" +
                    "\t set_id, project_id, locale_id, application_id, bucket_id, suite_id, env_id, run_id,testrail, loglink, lid, mobile, platform, browser, failed, skipped, passed, total, " +
                    "duration, start_time, end_time, time, by_user)\n" +
                    "\t VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?);";
            if(data.failed>0){
                status="Fail";
            }
            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(Insert_Set)) {
                preparedStatement.setInt(1, set_id);
                preparedStatement.setInt(2, project_id);
                preparedStatement.setInt(3, locale_id);
                preparedStatement.setInt(4, application_id);
                preparedStatement.setInt(5, bucket_id);
                preparedStatement.setInt(6, suite_id);
                preparedStatement.setInt(7, env_id);
                preparedStatement.setInt(8, run_id);
                preparedStatement.setString(9, data.testRail);
                preparedStatement.setString(10, data.logLink);
                preparedStatement.setLong(11, data.lid);
                preparedStatement.setBoolean(12, data.mobile);
                preparedStatement.setString(13, data.platform);
                preparedStatement.setString(14, data.browser);
                preparedStatement.setInt(15, data.failed);
                preparedStatement.setInt(16, data.skipped);
                preparedStatement.setInt(17, data.passed);
                preparedStatement.setInt(18, data.total);
                preparedStatement.setInt(19, data.duration);
                preparedStatement.setLong(20, data.start);
                preparedStatement.setLong(21, data.end);
                preparedStatement.setLong(22, data.time);
                preparedStatement.setString(23, data.user);
                System.out.println(preparedStatement);
                // Step 3: Execute the query or update query
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                log.warn("Failed to insert results into PostgreSQL db, message is =  " + e.getMessage());
            }
            String Insert_Scenario = "INSERT INTO scenario(\n" +
                    "\t scenario_id, set_id, run_id,scenario_name, testrail, feature_name, error_type, lid, testraillink, duration, start_time, " +
                    "end_time, total_steps, result, failed, skipped, passed, sauce_link, server_info, sauce_video," +
                    " sauce_html, comment, locale_id)\n" +
                    "\tVALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?);";
            try (Connection connection2 = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement2 = connection2.prepareStatement(Insert_Scenario)) {
                for (int i = 0; i < data.scenario.size(); i++) {
                    dataScenario = (DBResult.Scenario) data.scenario.get(i);
                    String scenario_value = Long.toString(dataScenario.time).substring(8, 13);
                    scenario_id = Integer.parseInt(scenario_value);
                    preparedStatement2.setInt(1, scenario_id);
                    preparedStatement2.setInt(2, set_id);
                    preparedStatement2.setInt(3, run_id);
                    preparedStatement2.setString(4, dataScenario.scenarioName);
                    scenarios.add(dataScenario.scenarioName);
                    scenariIds.add(scenario_id);
                    preparedStatement2.setString(5, dataScenario.testRail);
                    preparedStatement2.setString(6, "#"+dataScenario.featureName);
                    preparedStatement2.setString(7, dataScenario.errorType);
                    preparedStatement2.setLong(8, dataScenario.lid);
                    preparedStatement2.setString(9, dataScenario.testRailLink);
                    preparedStatement2.setInt(10, dataScenario.duration);
                    preparedStatement2.setLong(11, dataScenario.start);
                    preparedStatement2.setLong(12, dataScenario.end);
                    preparedStatement2.setLong(13, dataScenario.totalSteps);
                    preparedStatement2.setString(14, dataScenario.result);
                    preparedStatement2.setInt(15, dataScenario.failed);
                    preparedStatement2.setInt(16, dataScenario.skipped);
                    preparedStatement2.setInt(17, dataScenario.passed);
                    preparedStatement2.setString(18, dataScenario.sauceLink);
                    preparedStatement2.setString(19, dataScenario.serverInfo);
                    preparedStatement2.setString(20, dataScenario.sauceVideo);
                    preparedStatement2.setString(21, dataScenario.sauceHtml);
                    preparedStatement2.setString(22, dataScenario.comment);
                    preparedStatement2.setInt(23, locale_id);
                    System.out.println(preparedStatement2);
                    preparedStatement2.addBatch();
                }
//                 Step 3: Execute the query or update query
                preparedStatement2.executeBatch();
            } catch (Exception e) {
                log.warn("Failed to insert results into PostgreSQL db, message is =  " + e.getMessage());
            }
            //get scenario names
            for (int i = 0; i < scenarios.size(); i++) {
                int finalI = i;
                List<DBResult.Step> scenario = data.step.stream().filter((s) -> s.scenarioName.equals(scenarios.get(finalI))).collect(Collectors.toList());
                // scenario = data.step.stream().map(p -> p.scenarioName.equals(data.scenario.get(finalI).scenarioName)).collect(Collectors.toList());
                //}
                String Insert_step = "INSERT INTO step(\n" +
                        "\t step_id,scenario_id, run_id,step_name, testrail, lid,duration, start_time, end_time, result,scenarioName,comment)\n" +
                        "\t VALUES (?,?,?, ?, ?, ?,?,?,?,?,?,?);";
                try (Connection connection2 = DriverManager.getConnection(url, user, password);
                     PreparedStatement preparedStatement3 = connection2.prepareStatement(Insert_step)) {
                    for (int j = 0; j < scenario.size(); j++) {
                        int step_id = Integer.parseInt(DateTimeFormatter.ofPattern("HHmmssSSS").format(LocalDateTime.now()));
                        int finalI2 = j;
                        preparedStatement3.setInt(1, step_id + CommonUtilities.getRandomNumber(10, 1000));
                        //  List<DBResult.Scenario> scen = data.scenario.stream().filter((s) -> s.scenarioName.equals(scenario.get(finalI2).scenarioName)).collect(Collectors.toList());
                        preparedStatement3.setInt(2, scenariIds.get(i));
                        preparedStatement3.setLong(3, run_id);
                        preparedStatement3.setString(4, scenario.get(j).step);
                        preparedStatement3.setString(5, scenario.get(j).testRail);
                        preparedStatement3.setLong(6, scenario.get(j).lid);
                        preparedStatement3.setLong(7, scenario.get(j).duration);
                        preparedStatement3.setLong(8, scenario.get(j).start);
                        preparedStatement3.setLong(9, scenario.get(j).end);
                        preparedStatement3.setString(10, scenario.get(j).result);
                        preparedStatement3.setString(11, scenario.get(j).scenarioName);
                        String failReason="";
                        if(scenario.get(j).result.equals("FAILED")){
                            List<DBResult.Scenario> scenarioFailed = data.scenario.stream().filter((s) -> s.scenarioName.equals(scenario.get(finalI2).scenarioName)).collect(Collectors.toList());
                            failReason = scenarioFailed.get(0).comment;
                            preparedStatement3.setString(12, failReason);
                        }else {
                            preparedStatement3.setString(12, failReason);
                        }

                        System.out.println(preparedStatement3);
//                preparedStatement2.setLong(6, dataScenario.v);
                        preparedStatement3.addBatch();
                    }
                    System.out.println(preparedStatement3);
//                     Step 3: Execute the /query or update query
                    preparedStatement3.executeBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                    log.warn("Failed to insert results into PostgreSQL db, message is =  " + e.getMessage());
                }
            }
        } catch (Exception e) {

        }finally {
            writeFinalStatus(url,user,password,status,set_id);
        }
    }

    public static void writeFinalStatus(String url, String user, String password, String status, int set_id) {
        String updateValues = "Update Mobe_Dev_Local.tb_TestSuite_Execution_Results set IsExecuted = 1 , Status ='"+ status + "' where TestExecution_Result_Id = " + set_id + "";
        System.out.println("Final query::::"+updateValues);
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(updateValues)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  static void insertHealingData(String url, String user, String password,int set_id) {
        String url_he = "jdbc:postgresql://localhost:5432/postgres";
        String user_he = "healenium_user";
        String password_he = "YDk2nmNs4s9aCP6K";
        String data="";
        String uid="";
        try {
            //getdata from postgres
            String SQL = "select uid,elements from public.report order by \"create_date\" desc";
            try (Connection connection = DriverManager.getConnection(url_he, user_he, password_he);
                 Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(SQL)) {
                rs.next();
                uid = rs.getString(1);
                data = rs.getString(2);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }


            String query = "INSERT IGNORE INTO `tb_Healing_Data` (`Uid`, `Result`, `TestExecution_Id`) VALUES(?,?,?);";
            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(0, uid);
                preparedStatement.setString(1, data);
                preparedStatement.setInt(2, set_id);
                System.out.println("Healing query" + preparedStatement);
                // Step 3: Execute the query or update query
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();

            }

        }catch (Exception e)
        {

        }
    }
}