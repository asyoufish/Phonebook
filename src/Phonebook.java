import java.math.BigInteger;

//Phonebook class for creating list of people
public class Phonebook {
    String name;
    private String address;
    private BigInteger phoneNumber;

    /**
     * Phonebook() Empty Constructor
     * <p>
     * Phonebook() create an empty phonebook object
     */
    public Phonebook() {
        this.setName("");
        this.setAddress("");
        this.setPhoneNumber(BigInteger.valueOf(0));
    }

    /**
     * Phonebook() Constructor
     * <p>
     * Phonebook() populate the phone book object with name, address and phone number. Phone number have to be integer.
     * @param name Name of the Person
     * @param address Address of the Person
     * @param phoneNumber Phone number of the Person
     */
    public Phonebook(String name, String address, BigInteger phoneNumber) {
        this.setName(name);
        this.setAddress(address);
        this.setPhoneNumber(phoneNumber);
    }

    /**
     * Phone number getter
     * 
     * @return The phone number of this object
     */
    public BigInteger getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * Phone number setter
     * 
     * @param phoneNumber Phone number of the Person
     */
    public void setPhoneNumber(BigInteger phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Address getter
     * 
     * @return The address of this object
     */
    public String getAddress() {
        return address;
    }

    /**
     * Address setter
     * 
     * @param address Address of the Person
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Name getter
     * 
     * @return The name of this Person
     */
    public String getName() {
        return name;
    }
    /**
     * Name setter
     * 
     * @param name Name of the Person
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ("Name: " + name + "; Number: " + phoneNumber + "; Address: " + address);
    }

   
}
