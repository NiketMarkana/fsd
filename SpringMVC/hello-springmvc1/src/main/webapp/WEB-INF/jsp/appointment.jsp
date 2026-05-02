<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Patient Appointment Registration</title>
</head>

<body>

<h1>${mainHeader}</h1>
<h2>Patient Appointment Registration</h2>

<form:form method="post" action="addappointment" modelAttribute="patient">

    <table>

        <tr>
            <td>Name:</td>
            <td>
                <form:errors path="patientName" style="color:red"/> <br/>
                <form:input path="patientName"/>
            </td>
        </tr>

        <tr>
            <td>Contact No.:</td>
            <td>
                <form:errors path="patientContact" style="color:red"/> <br/>
                <form:input path="patientContact"/>
            </td>
        </tr>

        <tr>
            <td>Gender:</td>
            <td>
                <form:errors path="patientGender" style="color:red"/> <br/>
                <form:radiobutton path="patientGender" value="Male" label="Male"/>
                <form:radiobutton path="patientGender" value="Female" label="Female"/>
            </td>
        </tr>

        <tr>
            <td>Date of Birth:</td>
            <td>
                <form:errors path="patientDoB" style="color:red"/> <br/>
                <form:input path="patientDoB" type="date"/>
            </td>
        </tr>

        <tr>
            <td>Allergies:</td>
            <td>
                <form:checkboxes path="patientAllergies" items="${allergyList}"/>
            </td>
        </tr>

        <tr>
            <td colspan="2">Address Details:</td>
        </tr>

        <tr>
            <td>Street:</td>
            <td>
                <form:errors path="patientAddress.street" style="color:red"/> <br/>
                <form:input path="patientAddress.street"/>
            </td>
        </tr>

        <tr>
            <td>City:</td>
            <td>
                <form:errors path="patientAddress.city" style="color:red"/> <br/>
                <form:input path="patientAddress.city"/>
            </td>
        </tr>

        <tr>
            <td>State:</td>
            <td>
                <form:errors path="patientAddress.state" style="color:red"/> <br/>
                <form:input path="patientAddress.state"/>
            </td>
        </tr>

        <tr>
            <td>Country:</td>
            <td>
                <form:errors path="patientAddress.country" style="color:red"/> <br/>
                <form:select path="patientAddress.country">
                    <form:option value="" label="-- Select Country --"/>
                    <form:options items="${countryList}"/>
                </form:select>
            </td>
        </tr>

        <tr>
            <td>Pincode:</td>
            <td>
                <form:errors path="patientAddress.pincode" style="color:red"/> <br/>
                <form:input path="patientAddress.pincode"/>
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <input type="submit" value="Add my Appointment!"/>
            </td>
        </tr>

    </table>

</form:form>

</body>
</html>