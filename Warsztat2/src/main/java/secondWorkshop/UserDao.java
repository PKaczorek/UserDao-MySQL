package secondWorkshop;

import org.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    public Connection connection;
    public UserDao() {
        try {
            connection = DbUtil.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static final String CREATE_USER_QUERY = "INSERT INTO workShop.users (email, username, password) VALUES (?, ?, ?)";
    public  User create(User user) {
        try (Connection connect = DbUtil.connect()) {
            PreparedStatement statement = connect.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            } return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static final String UPDATE_USER_QUERY = "UPDATE users SET email = ?,username = ?, password = ?  WHERE id = ?";
        public void update(User user) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static final String READ_USER_QUERY ="SELECT * FROM users WHERE id = (?)";
    public User read(int userid) {
        try (PreparedStatement statement = connection.prepareStatement(READ_USER_QUERY)) {
            statement.setInt(1, userid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    public void delete(int id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER_QUERY)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

private static final String FIND_ALL_USERS_QUERY = "SELECT * FROM users;";
    public User[] findAll() {
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS_QUERY)) {
            User[] users = new User[0];
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                users = addToArray(user, users);
            }
        return users;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return new User[0];
    }

    private static User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = u;
        return tmpUsers;
    }


    public String hashPassword(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt());
        }

}
