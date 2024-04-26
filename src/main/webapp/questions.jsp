<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Questions</title>
</head>
    <body>
        <%
            if ((session.getAttribute("username") == null)) { // If user is not logged in, redirect to login page
                response.sendRedirect("login");
            }
            String username = (String)session.getAttribute("username");
            // out.println(QuestionUtil.checkUser(username)); prints user
        %>
        <jsp:include page="/WEB-INF/components/navbar.jsp" />
        <h1>Questions Page</h1>
        
            <% if (QuestionUtil.checkUser(username) == 3){ // user%>
            
				<style>
				  .container {
				    display: flex;
				    justify-content: space-between;
				  }
				  .box {
				    width: 45%;
				    padding: 10px;
				    border: 0px;
				    display: flex;
				    flex-direction: column;
				    align-items: center;
				  }
				  textarea {
				    width: 85%;
				  }
				</style>
				</head>
				<body>
				
				<div class="container">
				  <div class="box">
						
					<!-- Form to submit a question -->
	                <form method="post" action="">
	                    <h3><label for="question">Ask a Question: <span style="color: red;">*</span></h3></label>
	                    <textarea name="question" rows="5" cols="50" required></textarea><br>
	                    <input type="submit" name="question" value="Ask My Question">
	                </form>
	                <br>
	                
				  </div>
				  <div class="box">
				  	<h3> Select Questions </h3>
				  	<form method="post" action="">
						<select name="viewChoice" required>
						<option value=""> </option>
						<option value="myQuestions"> View My Questions </option>
						<option value="allQuestions"> View All Questions </option>					
						</select>
						<input type="submit" name="viewChoice" value="Submit">
					</form>
						<p> or </p>
					<form method="post" action="">
						<h3> Search for questions with a keyword </h3>
						<input type = "text" name = "keyword" required>
						<input type = "submit" name = "keywordSubmit" value = "Search">
				  </form>
				  </div>
				</div>

				
                <style>
                    table {
                        border: 1px solid gray;
                        border-collapse: collapse;
                    }
                    th:first-child,
				    td:first-child {
				        width: 110px;
				    }
                    th, td {
                        width: 250px;
                        height: 30px;
                        border: 1px solid gray;
                        padding: 2px;
                    }
                    tr:first-child td{
                        height: 50px;
                    }
                </style>
        

                <% if ("POST".equals(request.getMethod())) { // check if a form is submitted
					if (request.getParameter("question") != null) { // Check if the question form was submitted
                        String question = request.getParameter("question");
                        if (!QuestionUtil.existsInTable(question)){
                            if (QuestionUtil.askQuestion(username, question)){
                                // success
                            }
                            else{
                                out.println("<p style='color:red;'>Error adding question to database </p>");
                            }
                        }
                        else{
                            out.println("<p style='color:red;'>Duplicate question </p>");
                        }	   
                    }
					else if (request.getParameter("viewChoice") != null) { // Check if the viewChoice form was submitted
                        String selectedOption = request.getParameter("viewChoice");
                        if (selectedOption.equals("myQuestions")){ // displays all user's questions %>
                            <table>
                            <h2> My Questions </h2>
                            <tr>
                                <td><h3>Question ID</h3></td>
                                <td><h3>Questions</h3></td>
                                <td><h3>Answers</h3></td>
                            </tr>
                            <%
                            ArrayList<Integer> QIDs = QuestionUtil.getQIDs(username);
                            ArrayList<String> questions = QuestionUtil.getQuestions(username);
                            ArrayList<String> answers = QuestionUtil.getAnswers(username);
                
                            for (int i = 0; i < questions.size(); i++) {
                                int qid = QIDs.get(i);
                                String question = questions.get(i);
                                String answer = answers.get(i);
                            %>
                            <tr>
                                <td><%=qid %></td>
                                <td><%=question %></td>
                                <td><%=answer %></td>
                            </tr>
                            <% } %>
                            </table>
                        <%}
                        else if (selectedOption.equals("allQuestions")){ // displays all questions %>
                            <table>
                            <h2> All Questions </h2>
                            <tr>
                                <td><h3>Usernames</h3></td>
                                <td><h3>Questions</h3></td>
                                <td><h3>Answers</h3></td>
                            </tr>
                            <%
                            ArrayList<String> usernames = QuestionUtil.getUsernames();
                            ArrayList<String> questions = QuestionUtil.getQuestions();
                            ArrayList<String> answers = QuestionUtil.getAnswers();
                
                            for (int i = 0; i < questions.size(); i++) {
                                String usernameRecord = usernames.get(i);
                                String question = questions.get(i);
                                String answer = answers.get(i);
                            %>
                            <tr>
                                <td><%=usernameRecord %></td>
                                <td><%=question %></td>
                                <td><%=answer %></td>
                            </tr>
                            <% } %>
                        </table>
                        <%}
	                    else{ // something went wrong when choosing questions to view/browse
	                    	out.println("<p style='color:red;'> Error </p>");
	                    }
					}
                    else if (request.getParameter("keywordSubmit") != null){ // if a keyword was submitted
                        String keyword = request.getParameter("keyword");
                        Map<String, String> keyValueList = QuestionUtil.findKeywords(keyword);
                        if (keyValueList.size() > 0){ %>
                            <table>
                                <h2> Questions </h2>
                                <tr>
                                    <td><h3>Questions</h3></td>
                                    <td><h3>Answers</h3></td>
                                </tr>
                                <%
                                for (Map.Entry<String, String> entry : keyValueList.entrySet()) {
                                    String question = entry.getKey();
                                    String answer = entry.getValue();
                                %>
                                <tr>
                                    <td><%=question %></td>
                                    <td><%=answer %></td>
                                </tr>
                                <% } %>
                            </table> <%
                        }
                        else {
                            out.println("<p style='color:red;'> There were no questions with the keyword :( </p>");
                        }
                    }
                    else { // something went wrong on the main part of the page
                        out.println("<p style='color:red;'> Error </p>");
                    }
                }
            }
            
            else if (QuestionUtil.checkUser(username) == 2) { // customer rep %>
            	<style>
                table {
                    border: 1px solid gray;
                    border-collapse: collapse;
                }
                th, td {
                    width: 250px;
                    height: 30px;
                    border: 1px solid gray;
                    padding: 2px;
                }
                tr:first-child td{
                    height: 50px;
                }
                </style>
    
                <form method="post">
                    Question ID Number
                    <input type = "number" name = "qid" pattern="\d+" required>
                    <br>
                    <label for="answer">Answer a Question</label><br>
                    <br><textarea name="answer" rows="5" cols="50" required></textarea>
                    <br><input type="submit" name="submit_answer" value="Answer Question">
                    <br>
                </form>    
                <br>

                <table>
                    <tr>
                        <td><h3>Question ID</h3></td>
                        <td><h3>Questions</h3></td>
                        <td><h3>Answers</h3></td>
                    </tr>
                    <%
                    ArrayList<Integer> IDs = QuestionUtil.getQIDs();
                    ArrayList<String> questions = QuestionUtil.getQuestions();
                    ArrayList<String> answers = QuestionUtil.getAnswers();
        
                    for (int i = 0; i < questions.size(); i++) {
                        int question_id = IDs.get(i);
                        String question = questions.get(i);
                        String answer = answers.get(i);
                    %>
                    <tr>
                        <td><%=question_id %></td>
                        <td><%=question %></td>
                        <td><%=answer %></td>
                    </tr>
                    <% } %>
                </table>
                
                <%if ("POST".equals(request.getMethod())) {
                    int qid = Integer.parseInt(request.getParameter("qid"));
                    String answer = request.getParameter("answer");
                    if (!QuestionUtil.validateQuestionID(qid)){ // Checks qid is a valid question_id
                        out.println("<p style='color:red;'>Not a valid question number.</p>");
                    }
                    else if (QuestionUtil.answerQuestion(qid, answer)){ // entered question and qid into database
                        response.sendRedirect("questions");
                        out.println("success");
                    }
	                else{
	                        out.println("<p style='color:red;'>Error Try again.</p>");
	                }
                }
            }
            
   			else if (QuestionUtil.checkUser(username) == 1){ // admin %>
   			
   				
   			<% }
   			else{
   				out.println("<p style='color:red;'> Error </p>");
   			}
   			%>

    </body>
</html>