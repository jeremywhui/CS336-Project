package com.cs336.pkg;

import java.sql.*;
import java.util.*;

/**
 * This class provides methods for asking, answering and validating questions
 */
public class QuestionUtil {
    

    /**
     * check what user role someone has
     *
     * @param username of user in question
     * @return 3 - user
     *         2 - customer rep
     *         1 - admin
     */
    public static int checkUser (String username){
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String insertQuery = "SELECT role FROM end_user where username = ?";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();


            while (result.next()) { // while there are results
                if (result.getString("role") == null) {
                    return 3;
                } else if (result.getString("role").equals("Customer Representative")) {
                    return 2;
                } else if (result.getString("role").equals("Admin")) {
                    return 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

    /**
     * adds a question to the table from a user
     *
     * @param question 
     * @return true or false based whether it exists already
     */
    public static boolean existsInTable (String question) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String insertQuery = "SELECT * FROM question WHERE question = ?";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, question);
            ResultSet result = pstmt.executeQuery();
            if (result.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * adds a question to the table from a user
     *
     * @param question 
     * @return true or false based on success
     */
    public static boolean askQuestion (String username, String userQuestion){
    	ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
        	// INSERT INTO table_name (column1, column2, column3,etc) VALUES (value1, value2, value3, etc);
        	String insertQuery = "INSERT INTO question (username, question) VALUES (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, username);
            pstmt.setString(2, userQuestion);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * returns an a String ArrayList of questions the user has asked
     *
     * @param username the username of the user asking the question
     * @return ArrayList of Strings of questions the user has asked
     */
    public static ArrayList<String> getQuestions(String username) {
        ArrayList<String> listOfQuestions = new ArrayList<String>();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT question from question where username = ? order by question_id";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) { // while there are results
                String question = result.getString("question"); // get question from current row
                listOfQuestions.add(question); // add questions to arraylist
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return listOfQuestions;
    }

    /** returns an a String ArrayList of answers to the answers the user has asked
     *
     * @param username the username of the user asking the question
     * @return ArrayList of Strings of answers the user has asked
     */
    public static ArrayList<String> getAnswers(String username) {
        ArrayList<String> listOfAnswers = new ArrayList<String>();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT answer from question where username = ? order by question_id";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) { // while there are results
                String question = result.getString("answer"); // get answer from current row
                listOfAnswers.add(question); // add answer to arraylist
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return listOfAnswers;
    }
    
    /** returns an a String ArrayList of question_ids to the questions the user has asked
    *
    * @param username the username of the user asking the question
    * @return ArrayList of int of question_ids the user has asked
    */
   public static ArrayList<Integer> getQIDs(String username) {
       ArrayList<Integer> listOfQIDs = new ArrayList<Integer>();
       ApplicationDB db = new ApplicationDB();
       Connection con = db.getConnection();

       try (Statement stmt = con.createStatement()) {
           String query = "SELECT question_id from question where username = ? order by question_id";
           PreparedStatement pstmt = con.prepareStatement(query);
           pstmt.setString(1, username);
           ResultSet result = pstmt.executeQuery();

           while (result.next()) { // while there are results
               Integer qid = result.getInt("question_id"); // get answer from current row
               listOfQIDs.add(qid); // add answer to arraylist
           }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           if (con != null) {
               try {
                   con.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
       }
       return listOfQIDs;
   }

    /**
     * returns an a String ArrayList of questions asked
     *
     * @param void
     * @return ArrayList of Strings of all questions asked
     */
    public static ArrayList<String> getQuestions() {
        ArrayList<String> listOfQuestions = new ArrayList<String>();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT question from question ORDER BY question_id";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) { // while there are results
                String question = result.getString("question"); // get question from current row
                listOfQuestions.add(question); // add questions to arraylist
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return listOfQuestions;
    }

    /**
     * returns an a String ArrayList of answers
     *
     * @param void
     * @return ArrayList of Strings of all answers
     */
    public static ArrayList<String> getAnswers() {
        ArrayList<String> listOfAnswers = new ArrayList<String>();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT answer from question ORDER BY question_id";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) { // while there are results
                String answer = result.getString("answer"); // get question from current row
                listOfAnswers.add(answer); // add questions to arraylist
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return listOfAnswers;
    }
    
    /**
     * returns an a String ArrayList of question_id
     *
     * @param void
     * @return ArrayList of ints of all question_ids
     */
    public static ArrayList<Integer> getQIDs() {
        ArrayList<Integer> listOfIDs = new ArrayList<Integer>();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT question_id FROM question ORDER BY question_id;";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) { // while there are results
                Integer id = result.getInt("question_id"); // get question from current row
                listOfIDs.add(id); // add questions to arraylist
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return listOfIDs;
    }

    /**
     * returns an a String ArrayList of usernames of those who asked questions
     *
     * @param void the username of the user asking the question
     * @return ArrayList of int of usernames of
     */
    public static ArrayList<String> getUsernames() {
        ArrayList<String> listOfUsernames = new ArrayList<String>();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT username from question order by question_id;";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) { // while there are results
                String username = result.getString("username"); // get answer from current row
                listOfUsernames.add(username); // add answer to arraylist
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return listOfUsernames;
    }

    /**
     * returns an a String HashMap of <Question, Answer> pairs that have the target keyword
     *
     * @param keyword
     * @return ArrayList of <Question, Answer> pairs
     */
    public static Map<String, String> findKeywords(String keyword) {
        Map<String, String> keyValueList = new HashMap<>();
        // ArrayList<Integer> question_ids = new ArrayList<Integer>();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT question, answer, question_id from question where question LIKE ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, keyword + "%");
            ResultSet result = pstmt.executeQuery();

            while (result.next()) { // while there are results                
                String question = result.getString("question");
                String answer = result.getString("answer");
                int qid = result.getInt("question_id");

                keyValueList.put(question, answer);
                // if (!question_ids.contains(qid)){
                //     keyValueList.put(question, answer);
                //     question_ids.add(qid);
                // }                
            }

            String query2 = "SELECT question, answer, question_id from question where question LIKE ?";
            PreparedStatement pstmt2 = con.prepareStatement(query2);
            pstmt2.setString(1, "%" + keyword);
            ResultSet result2 = pstmt2.executeQuery();

            while (result2.next()) { // while there are results
                String question = result2.getString("question");
                String answer = result2.getString("answer");
                int qid = result2.getInt("question_id");

                keyValueList.put(question, answer);
                // if (!question_ids.contains(qid)) {
                //     keyValueList.put(question, answer);
                //     question_ids.add(qid);
                // }
            }

            String query3 = "SELECT question, answer, question_id from question where question LIKE ?";
            PreparedStatement pstmt3 = con.prepareStatement(query3);
            pstmt3.setString(1, "%" + keyword + "% ");
            ResultSet result3 = pstmt3.executeQuery();

            while (result3.next()) { // while there are results
                String question = result3.getString("question");
                String answer = result3.getString("answer");
                int qid = result3.getInt("question_id");

                keyValueList.put(question, answer);
                // if (!question_ids.contains(qid)) {
                //     keyValueList.put(question, answer);
                //     question_ids.add(qid);
                // } 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return keyValueList;
    }

    /**
     * confirms a valid question_id
     *
     * @param question_id
     * @return boolean for valid question_id
     */
    public static boolean validateQuestionID (int qid) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM question WHERE question_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, qid);
            ResultSet result = pstmt.executeQuery();

            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    /**
     * customer rep answers question and updates database
     *
     * @param question_id
     * @param answer
     * @return boolean for successful addition
     */
    public static boolean answerQuestion (int qid, String answer){
    	ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (PreparedStatement pstmt = con.prepareStatement("UPDATE question SET answer = ? WHERE question_id = ?")) {
            pstmt.setString(1, answer);
            pstmt.setInt(2, qid);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
}