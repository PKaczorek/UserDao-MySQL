package secondWorkshop;

public class Main {

    private String id;
    private String userName;
    private String email;

    @Override
    public String toString() {
        return "User" + " id=" + id + " |" + " userName= " + userName + " |" + " email= " + email;
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User userTest = new User();
        int option = 1;

        switch (option) {

            case 1:
                userTest.setUserName("");
                userTest.setEmail("");
                userTest.setPassword("");
                userDao.create(userTest);
                System.out.println("User: " + userTest + " created and added to database");
                break;

            case 2:
                userTest.setId(1);
                userTest.setEmail("");
                userTest.setUserName("");
                userTest.setPassword("");
                userDao.update(userTest);
                System.out.println("Users info has been changed to: " + userTest);
                break;

            case 3:
                int userId = userTest.getId();
                User readUser = userDao.read(1);
                System.out.println(readUser + " has been loaded");
                break;

            case 4:
                int userIdShown = userTest.getId();
                userDao.delete(8);
                System.out.println("User has been deleted from database");
                break;

            case 5:
                User[] allUsers = userDao.findAll();
                for (User user : allUsers) {
                    System.out.println(user);
                }
                break;

            default:
                System.out.println("Invalid option.");
        }
    }
}
