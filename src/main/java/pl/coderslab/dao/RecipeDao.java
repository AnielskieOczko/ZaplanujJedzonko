package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

public class RecipeDao {

  private static final String CREATE_RECIPE_QUERY = "INSERT INTO recipe (name, ingredients, description, created, updated, preparation_time, preparation, admin_id) VALUES (?,?,?, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,?,?,?);";
  private static final String DELETE_RECIPE_QUERY = "DELETE FROM recipe where id = ?;";
  private static final String FIND_ALL_RECIPE_QUERY = "SELECT * from recipe;";
  private static final String READ_RECIPE_QUERY = "SELECT * from recipe where id = ?;";
  private static final String UPDATE_RECIPE_QUERY = "UPDATE recipe SET name = ?, ingredients = ?, description = ?, updated = CURRENT_TIMESTAMP, preparation_time = ?, preparation = ?, admin_id = ? where id = ?;";

  /**
   * Create recipe
   *
   * @param recipe
   * @return
   */

  public Recipe create(Recipe recipe) {
    try (Connection connection = DbUtil.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(CREATE_RECIPE_QUERY,
          Statement.RETURN_GENERATED_KEYS);

      statement.setString(1, recipe.getName());
      statement.setString(2, recipe.getIngredients());
      statement.setString(3, recipe.getDescription());
      statement.setInt(4, recipe.getPreparationTime());
      statement.setString(5, recipe.getPreparation());
      statement.setInt(6, recipe.getAdminId());
      statement.executeUpdate();

      ResultSet resultSet = statement.getGeneratedKeys();

      if (resultSet.next()) {
        recipe.setId(resultSet.getInt(1));
      }
      return recipe;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  ;

  /**
   * Get recipe by id
   *
   * @param id
   * @return
   */

  public Recipe read(int id) {

    try (Connection connection = DbUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(READ_RECIPE_QUERY)
    ) {
      statement.setInt(1, id);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        Recipe recipe = new Recipe();
        recipe.setId(resultSet.getInt("id"));
        recipe.setName(resultSet.getString("name"));
        recipe.setIngredients(resultSet.getString("ingredients"));
        recipe.setDescription(resultSet.getString("description"));
        recipe.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
        recipe.setUpdated(resultSet.getTimestamp("updated").toLocalDateTime());
        recipe.setPreparationTime(resultSet.getInt("preparationTime"));
        recipe.setPreparation(resultSet.getString("preparation"));
        recipe.setAdminId(resultSet.getInt("adminId"));
        return recipe;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  ;


  /**
   * Update recipe
   *
   * @param recipe
   */
  public void update(Recipe recipe) {
    try (Connection conn = DbUtil.getConnection()) {
      PreparedStatement statement = conn.prepareStatement(UPDATE_RECIPE_QUERY);
      statement.setString(1, recipe.getName());
      statement.setString(2, recipe.getIngredients());
      statement.setString(3, recipe.getDescription());
      statement.setInt(4, recipe.getPreparationTime());
      statement.setString(5, recipe.getPreparation());
      statement.setInt(6, recipe.getAdminId());
      statement.setInt(7, recipe.getId());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /**
   * Remove book by id
   *
   * @param id
   */

  public void delete(int id) {
    try (Connection connection = DbUtil.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(DELETE_RECIPE_QUERY);
      statement.setInt(1, id);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /**
   * Return all recipes
   *
   * @return
   */
  public List<Recipe> findAll() {
    try (Connection connection = DbUtil.getConnection()) {
      List<Recipe> recipes = new ArrayList<>();

      PreparedStatement statement = connection.prepareStatement(FIND_ALL_RECIPE_QUERY);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        Recipe recipe = new Recipe();
        recipe.setId(resultSet.getInt("id"));
        recipe.setName(resultSet.getString("name"));
        recipe.setIngredients(resultSet.getString("ingredients"));
        recipe.setDescription(resultSet.getString("description"));
        recipe.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
        recipe.setUpdated(resultSet.getTimestamp("updated").toLocalDateTime());
        recipe.setAdminId(resultSet.getInt("admin_id"));
        recipe.setPreparationTime(resultSet.getInt("preparation_time"));
        recipe.setPreparation(resultSet.getString("preparation"));
        recipes.add(recipe);
      }
      return recipes;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }


}

