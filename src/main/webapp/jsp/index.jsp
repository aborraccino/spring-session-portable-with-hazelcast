<%@ page import="com.worldpay.spring.session.model.Contact" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <%
        Contact contact = new Contact();
        if(session.getAttribute("scopedTarget.contact") != null){
            contact = (Contact) session.getAttribute("scopedTarget.contact");
        }
    %>
<h1>Read Contacts V1</h1>
<p>You are currently logged in as <span id="un" style="font-weight: bold"><c:out value="${pageContext.request.remoteUser}"/></span>.</p>
<table border="2" width="70%" cellpadding="2">
    <tr>
        <th>Name</th>
        <th>Surname</th>
        <th>Country</th>
    </tr>
    <tr>
        <td> <% out.println(contact.getName()); %> </td>
        <td> <% out.println(contact.getSurname()); %> </td>
        <td> <% out.println(contact.getCountry()); %> </td>
    </tr>
</table>

<h1>Update Contact</h1>
<form:form method="post" action="/update-contact/" modelAttribute="contact">
    <table>
        <tr>
            <td>Name: </td>
            <td><form:input path="name"/></td>
        </tr>
        <tr>
            <td>Surname: </td>
            <td><form:input path="surname"/></td>
        </tr>
        <tr>
            <td>Country: </td>
            <td><form:input path="country"/></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Update" /></td>
        </tr>
    </table>
</form:form>
