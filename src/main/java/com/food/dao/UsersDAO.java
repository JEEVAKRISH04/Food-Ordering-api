package com.food.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.food.model.Users;
import com.food.utility.UtilityClass;
import com.tap.enums.UserRole;

public class UsersDAO {

    // SQL Queries
	String sql = "INSERT INTO users (name, email, phone_no, username, role, password, address) VALUES (?, ?, ?, ?, ?, ?, ?)";



    private static final String SELECT_QUERY = "SELECT * FROM users WHERE userid = ?";
    private static final String UPDATE_QUERY = "UPDATE users SET name = ?, email = ?, phone_no = ?, password = ?, address = ?, role = ? WHERE userid = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE userid = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM users";
    private static final String SELECT_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String SELECT_BY_USERNAME_AND_PASSWORD = "SELECT * FROM users WHERE username = ? AND password = ?";
    private static final String SELECT_DELIVERY_BOYS = "SELECT * FROM users WHERE role = 'DELIVERY_BOY'";


    private static final String GET_ALL_DELIVERY_BOYS_SQL = 
            "SELECT * FROM users WHERE role = 'DELIVERY_BOY'";
        
        private static final String GET_USER_BY_ID_SQL = 
            "SELECT * FROM users WHERE userid = ?";

    // Add a new user
    public void addUser(Users user) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, user.getName());
        params.put(2, user.getEmail());
        params.put(3, user.getPhoneNo());
        params.put(4, user.getUserName());
        params.put(5, user.getRole().toString());
        params.put(6, user.getPassword());
        params.put(7, user.getAddress());

        try {
            UtilityClass.updateObject(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get a user by userId
    public Users getUser(int userId) {
        Users user = null;
        try {
            List<Map<String, String>> result = UtilityClass.executeQueryForPreview(SELECT_QUERY, List.of(userId));
            if (!result.isEmpty()) {
                Map<String, String> row = result.get(0);
                user = new Users(
                        Integer.parseInt(row.get("userId")),
                        row.get("name"),
                        row.get("email"),
                        row.get("phoneNo"),
                        row.get("username"),
                        UserRole.valueOf(row.get("role")),
                        row.get("password"),
                        row.get("address")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Update an existing user
    public void updateUser(Users user) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, user.getName());
        params.put(2, user.getEmail());
        params.put(3, user.getPhoneNo());
        params.put(4, user.getPassword());
        params.put(5, user.getAddress());
        params.put(6, user.getRole().toString());
        params.put(7, user.getUserId());

        try {
            UtilityClass.updateObject(UPDATE_QUERY, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a user by userId
    public void deleteUser(int userId) {
        try (Connection connection = UtilityClass.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
            
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Get all users
    public List<Users> getAllUsers() {
        List<Users> userList = new ArrayList<>();
        try {
            List<Map<String, String>> result = UtilityClass.executeQuery(SELECT_ALL_QUERY);
            for (Map<String, String> row : result) {
                Users user = new Users(
                        Integer.parseInt(row.get("userid")),
                        row.get("name"),
                        row.get("email"),
                        row.get("phoneno"),
                        row.get("username"),
          
                        UserRole.valueOf(row.get("role")),
                        row.get("password"),
                        row.get("address")
                );
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    // Get a user by email and password (for login)
    public Users getUserByEmailAndPassword(String email, String password) {
        Users user = null;
        try {
            List<Map<String, String>> result = UtilityClass.executeQueryForPreview(SELECT_BY_EMAIL_AND_PASSWORD, List.of(email, password));
            if (!result.isEmpty()) {
                Map<String, String> row = result.get(0);
                user = new Users(
                        Integer.parseInt(row.get("userId")),
                        row.get("name"),
                        row.get("email"),
                        row.get("phoneNo"),
                        row.get("username"),
                      
                        UserRole.valueOf(row.get("role")),
                        row.get("password"),
                        row.get("address")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Check if a user exists by email
    public boolean isUserExists(String email) {
        try {
            List<Map<String, String>> result = UtilityClass.executeQueryForPreview(SELECT_BY_EMAIL, List.of(email));
            return !result.isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean isValidUser(String userName, String password) {
        try {
            List<Map<String, String>> result = UtilityClass.executeQueryForPreview(SELECT_BY_USERNAME_AND_PASSWORD, List.of(userName, password));
            return !result.isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Users getUserByUserNameAndPassword(String userName, String password) {
        Users user = null;
        try {
            List<Map<String, String>> result = UtilityClass.executeQueryForPreview(SELECT_BY_USERNAME_AND_PASSWORD, List.of(userName, password));
            
            if (!result.isEmpty()) {
                Map<String, String> row = result.get(0);
                
                System.out.println("Fetched row from DB: " + row); // Debugging

                String userIdStr = row.get("userid");

                if (userIdStr != null && !userIdStr.isEmpty()) {
                    int userId = Integer.parseInt(userIdStr);

                    user = new Users(
                        userId,
                        row.get("name"),
                        row.get("email"),
                        row.get("phoneNo"),
                        row.get("username"),
                        UserRole.valueOf(row.get("role")),
                        row.get("password"),
                        row.get("address")
                    );
                } else {
                    System.out.println("Error: userId is null or empty in DB! Check DB query.");
                }
                
            } else {
                System.out.println("User not found for given credentials.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format for userId.");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Users getUserByUserNamePasswordAndRole(String userName, String password, UserRole role) {
        Users user = null;
        try {
            // SQL query: added role condition
            String SELECT_BY_USERNAME_PASSWORD_AND_ROLE = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";

            // Pass role.name() to convert Enum to String (e.g., "ADMIN")
            List<Map<String, String>> result = UtilityClass.executeQueryForPreview(
                SELECT_BY_USERNAME_PASSWORD_AND_ROLE,
                List.of(userName, password, role.name()) // role.name() gets "ADMIN", "CUSTOMER", etc.
            );

            if (!result.isEmpty()) {
                Map<String, String> row = result.get(0);
                System.out.println("Fetched row: " + row); // Debugging

                String userIdStr = row.get("userid");

                if (userIdStr != null && !userIdStr.isEmpty()) {
                    int userId = Integer.parseInt(userIdStr);

                    user = new Users(
                        userId,
                        row.get("name"),
                        row.get("email"),
                        row.get("phoneNo"),
                        row.get("username"),
                        UserRole.valueOf(row.get("role")), // Converting DB String to Enum
                        row.get("password"),
                        row.get("address")
                    );
                } else {
                    System.out.println("Error: userId is null or empty!");
                }

            } else {
                System.out.println("User not found for given username, password, and role.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid userId format.");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

  
    
 
      
        
      
       public List<Users> getAllDeliveryBoys() {
           List<Users> deliveryBoys = new ArrayList<>();

           try (Connection conn = UtilityClass.getConnection();
                PreparedStatement ps = conn.prepareStatement(GET_ALL_DELIVERY_BOYS_SQL)) {

               ResultSet rs = ps.executeQuery();

               while (rs.next()) {
                   Users user = new Users();
                   user.setUserId(rs.getInt("userid"));
                   user.setName(rs.getString("name"));
                   user.setEmail(rs.getString("email"));
                   user.setPhoneNo(rs.getString("phoneno"));
                   user.setRole(UserRole.valueOf(rs.getString("role")));
                   user.setAddress(rs.getString("address"));
                   
                   deliveryBoys.add(user);
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }

           return deliveryBoys;
       }

       /**
        * Get a user by ID
        * 
        * @param userId User ID to find
        * @return User object if found, null otherwise
        */
       public Users getUserById(int userId) {
           Users user = null;

           try (Connection conn = UtilityClass.getConnection();
                PreparedStatement ps = conn.prepareStatement(GET_USER_BY_ID_SQL)) {

               ps.setInt(1, userId);
               ResultSet rs = ps.executeQuery();

               if (rs.next()) {
                   user = new Users();
                   user.setUserId(rs.getInt("userid"));
                   user.setName(rs.getString("name"));
                   user.setEmail(rs.getString("email"));
                   user.setPhoneNo(rs.getString("phoneno"));
                   user.setRole(UserRole.valueOf(rs.getString("role")));
                   user.setAddress(rs.getString("address"));
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }

           return user;
       }

}
