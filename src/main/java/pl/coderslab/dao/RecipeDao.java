package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

public class RecipeDao {

  public static Logger logger = LogManager.getLogger(RecipeDao.class);
  private static final String CREATE_RECIPE_QUERY = "INSERT INTO recipe (name, ingredients, description, created, updated, preparation_time, preparation, admin_id) VALUES (?,?,?, ?,?,?,?,?);";
  private static final String DELETE_RECIPE_QUERY = "DELETE FROM recipe where id = ?;";
  private static final String FIND_ALL_RECIPE_QUERY = "SELECT * from recipe;";
  private static final String READ_RECIPE_QUERY = "SELECT * from recipe where id = ?;";
  private static final String UPDATE_RECIPE_QUERY = "UPDATE recipe SET name = ?, ingredients = ?, description = ?, updated = ?, preparation_time = ?, preparation = ?, admin_id = ? where id = ?;";

  private static final String COUNT_RECIPE_QUERY = "SELECT COUNT(*) AS recipe_count FROM recipe WHERE admin_id = ?";

  /**
   * Create recipe
   *
   * @param recipe: Recipe instance to be added to database
   * @return Recipe instance with generated id once record created successfully
   */

  public Recipe create(Recipe recipe) {
    try (Connection connection = DbUtil.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(CREATE_RECIPE_QUERY,
          Statement.RETURN_GENERATED_KEYS);

      statement.setString(1, recipe.getName());
      statement.setString(2, recipe.getIngredients());
      statement.setString(3, recipe.getDescription());
      statement.setTimestamp(4, Timestamp.from(recipe.getCreated()));
      statement.setTimestamp(5, Timestamp.from(recipe.getUpdated()));
      statement.setInt(6, recipe.getPreparationTime());
      statement.setString(7, recipe.getPreparation());
      statement.setInt(8, recipe.getAdminId());
      int result = statement.executeUpdate();

      if (result != 1) {
        throw new RuntimeException("Execute update returned " + result);
      }

      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.first()) {
          recipe.setId(generatedKeys.getInt(1));
          return recipe;
        } else {
          throw new RuntimeException("Generated key was not found");
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  ;

  /**
   * Get recipe by id
   *
   * @param id: of the recipe record to be returned by sql query
   * @return Recipe instance once any record retrieved from database
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
        recipe.setCreated(resultSet.getTimestamp("created").toInstant());
        recipe.setUpdated(resultSet.getTimestamp("updated").toInstant());
        recipe.setPreparationTime(resultSet.getInt("preparation_time"));
        recipe.setPreparation(resultSet.getString("preparation"));
        recipe.setAdminId(resultSet.getInt("admin_id"));
        return recipe;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  ;


  /**
   * Update recipe with data from recipe object once record with given id exists in database
   *
   * @param recipe: Recipe instance with id which is used to update particular record in database
   */
  public void update(Recipe recipe) {
    try (Connection conn = DbUtil.getConnection()) {
      PreparedStatement statement = conn.prepareStatement(UPDATE_RECIPE_QUERY);
      recipe.setUpdated(Instant.now());
      statement.setString(1, recipe.getName());
      statement.setString(2, recipe.getIngredients());
      statement.setString(3, recipe.getDescription());
      statement.setTimestamp(4, Timestamp.from(recipe.getUpdated()));
      statement.setInt(5, recipe.getPreparationTime());
      statement.setString(6, recipe.getPreparation());
      statement.setInt(7, recipe.getAdminId());
      statement.setInt(8, recipe.getId());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /**
   * Remove recipe by id
   *
   * @param id: id of the plan to be removed
   */

  public void delete(int id) {
    try (Connection connection = DbUtil.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(DELETE_RECIPE_QUERY);
      statement.setInt(1, id);
      int rowsAffected = statement.executeUpdate();
      if (rowsAffected == 0) {
        throw new NotFoundException("Plan record not found");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Return all recipes
   *
   * @return All recipes stored in database
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
        recipe.setCreated(resultSet.getTimestamp("created").toInstant());
        recipe.setUpdated(resultSet.getTimestamp("updated").toInstant());
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

  /**
   * Return Recipe count
   *
   * @return Numbers of recipes by AdminId
   */

  public int countRecipesByAdminId(int adminId) {
    int count = 0;
    try (Connection connection = DbUtil.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(COUNT_RECIPE_QUERY)) {
        statement.setInt(1, adminId);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            count = resultSet.getInt("recipe_count");
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }
}

