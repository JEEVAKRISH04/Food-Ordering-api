package com.food.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.food.model.Menu;
import com.food.utility.UtilityClass;

public class MenuDAO {
    
    private static final String INSERT_QUERY = "INSERT INTO menu (restaurantid, name, price, imagepath, category, description, isAvailable, ratings) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM menu WHERE menuId = ?";
    private static final String SELECT_QUERY = "SELECT * FROM menu WHERE menuId = ?";
    private static final String UPDATE_QUERY = "UPDATE menu SET name = ?, price = ?, imagepath = ?, category = ?, description = ?, isAvailable = ?, ratings = ? WHERE menuId = ?";
    private static final String SELECTALL_QUERY = "SELECT * FROM menu";
    private static final String SELECTBYRESTRO_QUERY = "SELECT * FROM menu WHERE restaurantId = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM menu";
    
    public void addMenu(Menu menu) {
        Map<Integer, Object> params = new HashMap<>();
    
       params.put(1,  menu.getRestaurantId());
        params.put(2, menu.getName());
        params.put(3, menu.getPrice());
        params.put(4, menu.getImagePath());
        params.put(5, menu.getCategory());
        params.put(6, menu.getDescription());
        params.put(7, menu.getIsAvailable());
        params.put(8, menu.getRatings());
        
        try {
            UtilityClass.updateObject(INSERT_QUERY, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteMenu(int menuId) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, menuId);
        
        try {
            UtilityClass.updateObject(DELETE_QUERY, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Menu getMenuById(int menuId) {
        List<Object> params = Collections.singletonList(menuId);
        List<Map<String, String>> result;
        
        try {
            result = UtilityClass.executeQueryForPreview(SELECT_QUERY, params);
            if (!result.isEmpty()) {
                Map<String, String> row = result.get(0);
                return new Menu(
                    Integer.parseInt(row.get("menuId")),
                    Integer.parseInt(row.get("restaurantId")),
                    row.get("name"),
                    Double.parseDouble(row.get("price")),
                    row.get("imagepath"),
                    row.get("category"),
                    row.get("description"),
                    Boolean.parseBoolean(row.get("isAvailable")),
                    Double.parseDouble(row.get("ratings"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void updateMenu(Menu menu) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, menu.getName());
        params.put(2, menu.getPrice());
        params.put(3, menu.getImagePath());
        params.put(4, menu.getCategory());
        params.put(5, menu.getDescription());
        params.put(6, menu.getIsAvailable());
        params.put(7, menu.getRatings());
        params.put(8, menu.getMenuId());
        
        try {
            UtilityClass.updateObject(UPDATE_QUERY, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Menu> getAllMenus() {
        List<Menu> menus = new ArrayList<>();
        
        try {
            List<Map<String, String>> result = UtilityClass.executeQuery(SELECT_ALL_QUERY);
            for (Map<String, String> row : result) {
                menus.add(new Menu(
                    Integer.parseInt(row.get("menuid")),
                    Integer.parseInt(row.get("restaurantid")),
                    row.get("name"),
                    Double.parseDouble(row.get("price")),
                    row.get("imagepath"),
                    row.get("category"),
                    row.get("description"),
                    Boolean.parseBoolean(row.get("isavailable")),
                    Double.parseDouble(row.get("ratings"))
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menus;
    }
    
    public List<Menu> getMenuByRestaurantId(int restaurantId) {
        List<Menu> menus = new ArrayList<>();
        List<Object> params = Collections.singletonList(restaurantId);
        
        try {
            List<Map<String, String>> result = UtilityClass.executeQueryForPreview(SELECTBYRESTRO_QUERY, params);
            for (Map<String, String> row : result) {
                menus.add(new Menu(
                    Integer.parseInt(row.get("menuid")),
                    Integer.parseInt(row.get("restaurantid")),
                    row.get("name"),
                    Double.parseDouble(row.get("price")),
                    row.get("imagepath"),
                    row.get("category"),
                    row.get("description"),
                    Boolean.parseBoolean(row.get("isAvailable")),
                    Double.parseDouble(row.get("ratings"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menus;
    }
    
    

        public List<Menu> searchMenuItems(String query) {
            List<Menu> menuList = new ArrayList<>();
            String sql = "SELECT * FROM menu WHERE name LIKE ?";
            List<Object> parameters = new ArrayList<>();
            parameters.add("%" + query + "%");

            try {
                List<Map<String, String>> results = UtilityClass.executeQueryForPreview(sql, parameters);
                for (Map<String, String> row : results) {
                    Menu menu = new Menu();
                    menu.setMenuId(Integer.parseInt(row.get("menuId")));
                    menu.setName(row.get("name"));
                    menu.setPrice(Integer.parseInt(row.get("price")));
                    menu.setImagePath(row.get("imagePath"));
                    // Set other fields if any
                    menuList.add(menu);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return menuList;
        }
        
        
        
        public List<Menu> searchMenus(String keyword) {
            List<Menu> menuList = new ArrayList<>();
            String searchKeyword = "%" + keyword + "%";

            List<Object> params = new ArrayList<>();
            params.add(searchKeyword);
            params.add(searchKeyword);

            String query = "SELECT * FROM menu WHERE name LIKE ? OR description LIKE ?";

            try {
                List<Map<String, String>> result = UtilityClass.executeQueryForPreview(query, params);
                for (Map<String, String> row : result) {
                    Menu menu = new Menu(
                        Integer.parseInt(row.get("menuid")),
                        Integer.parseInt(row.get("restaurantid")),
                        row.get("name"),
                        Double.parseDouble(row.get("price")), // Changed to double because price is double
                        row.get("imagepath"),
                        row.get("category"),
                        row.get("description"),
                        Boolean.parseBoolean(row.get("isAvailable")),
                        Double.parseDouble(row.get("ratings"))
                    );
                    menuList.add(menu);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return menuList;
        }


    }

    

