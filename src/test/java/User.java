import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)

public class User {
	
	private String name;
	private Integer age;
	private Double salary;
	@XmlAttribute
	private Long id;
	
	public User() {
		
	}
	
	public User(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", salary=" + salary + ", id=" + id + "]";
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIdade() {
		return age;
	}
	public void setIdade(Integer idade) {
		this.age = idade;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
	
}
