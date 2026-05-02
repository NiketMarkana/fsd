<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.Arrays" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Patient Appointment Registration Status</title>
</head>

<body>

<h1>${mainHeader}</h1>
<h2>Patient Appointment Registration Status</h2>

<p>${message}</p>

<table>

    <tr>
        <td>Patient Name:</td>
        <td>${patient.patientName}</td>
    </tr>

    <tr>
        <td>Patient Contact:</td>
        <td>${patient.patientContact}</td>
    </tr>

    <tr>
        <td>Patient Gender:</td>
        <td>${patient.patientGender}</td>
    </tr>

    <tr>
        <td>Patient Date of Birth:</td>
        <td>${patient.patientDoB}</td>
    </tr>

    <tr>
        <td>Patient's Allergies:</td>
        <td>
            <c:forEach var="allergy" items="${patient.patientAllergies}">
                ${allergy}<br/>
            </c:forEach>
        </td>
    </tr>

    <tr>
        <td colspan="2">Patient's Address Details:</td>
    </tr>

    <tr>
        <td>Street:</td>
        <td>${patient.patientAddress.street}</td>
    </tr>

    <tr>
        <td>City:</td>
        <td>${patient.patientAddress.city}</td>
    </tr>

    <tr>
        <td>State:</td>
        <td>${patient.patientAddress.state}</td>
    </tr>

    <tr>
        <td>Country:</td>
        <td>${patient.patientAddress.country}</td>
    </tr>

    <tr>
        <td>Pincode:</td>
        <td>${patient.patientAddress.pincode}</td>
    </tr>

</table>

</body>
</html>