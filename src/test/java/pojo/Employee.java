package pojo;
//pojo : encapsulation
//no arg const


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//optional : arg contr
//get  and setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {
    private String employee_id ;
    private String first_name;
    private String last_name;

    public Employee() {
    }

    public Employee(String employee_id, String first_name, String last_name) {
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }



    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    //        "email": "BERNST",
//        "phone_number": "590.423.4568",
//        "hire_date": "2007-05-21T04:00:00Z",
//        "job_id": "IT_PROG",
//        "salary": 6000,
//        "commission_pct": null,
//        "manager_id": 103,
//        "department_id": 60,
//        "links":


    @Override
    public String toString() {
        return "Employee{" +
                "employee_id='" + employee_id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }

}
