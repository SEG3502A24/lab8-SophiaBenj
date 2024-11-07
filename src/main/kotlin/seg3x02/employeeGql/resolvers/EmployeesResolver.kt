package seg3x02.employeeGql.resolvers

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import java.util.*
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput

@Controller
class EmployeesResolver(private val employeesRepository: EmployeesRepository) {

    @QueryMapping
    fun employees(): List<Employee> = employeesRepository.findAll()

    @QueryMapping
    fun employeeById(@Argument id: String): Employee? = employeesRepository.findById(id).orElse(null)

    @MutationMapping
    fun newEmployee(@Argument createEmployeeInput: CreateEmployeeInput): Employee {
        val employee = Employee(
    name = createEmployeeInput.name ?: "",
    dateOfBirth = createEmployeeInput.dateOfBirth ?: "",
    city = createEmployeeInput.city ?: "",
    salary = createEmployeeInput.salary ?: 0.0f,
    gender = createEmployeeInput.gender,
    email = createEmployeeInput.email
)

        employee.id = UUID.randomUUID().toString()
        return employeesRepository.save(employee)
    }

    @MutationMapping
    fun deleteEmployee(@Argument id: String): Boolean {
        if (employeesRepository.existsById(id)) {
            employeesRepository.deleteById(id)
            return true
        }
        return false
    }

    @MutationMapping
fun updateEmployee(@Argument id: String, @Argument createEmployeeInput: CreateEmployeeInput): Employee? {
    val employee = employeesRepository.findById(id).orElse(null) ?: return null
    employee.apply {
        name = createEmployeeInput.name ?: name
        dateOfBirth = createEmployeeInput.dateOfBirth ?: dateOfBirth
        city = createEmployeeInput.city ?: city
        salary = createEmployeeInput.salary ?: salary
        gender = createEmployeeInput.gender ?: gender
        email = createEmployeeInput.email ?: email
    }
    return employeesRepository.save(employee)
}

}