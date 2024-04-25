package pl.coderslab.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dto.MealDetailsDto;
import pl.coderslab.dto.PlanDto;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Plan;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanDao {

  public static Logger logger = LogManager.getLogger(PlanDao.class);

  public static final String CREATE_PLAN_QUERY = "INSERT INTO plan(name, description, created, admin_id) VALUES(?, ?, ?, ?);";
  public static final String READ_PLAN_QUERY = "SELECT * FROM plan WHERE id = ?;";
  public static final String FIND_ALL_PLANS_QUERY = "SELECT * FROM plan;";
  public static final String UPDATE_PLAN_QUERY = "UPDATE plan SET name = ?, description = ? WHERE id = ?;";
  public static final String DELETE_PLAN_QUERY = "DELETE FROM plan WHERE id = ?;";
  public static final String FIND_LAST_PLAN_QUERY = "SELECT id, name FROM plan  WHERE admin_id = ? ORDER BY id DESC LIMIT 1;";
  public static final String FIND_PLAN_DETAILS_QUERY = """
            SELECT recipe_plan.id        as id,
                  recipe_plan.meal_name as meal_name,
                  recipe.name           as recipe_name,
                  recipe.id,
                  day_name.name
           FROM recipe_plan
                    JOIN recipe ON recipe_plan.recipe_id = recipe.id
                    JOIN plan ON recipe_plan.plan_id = plan.id
                    JOIN day_name ON recipe_plan.day_name_id = day_name.id
           WHERE plan.id = ?
           order by day_name.display_order, recipe_plan.display_order;""";

  private static final String COUNT_PLAN_QUERY = "SELECT COUNT(*) AS plan_count FROM plan WHERE admin_id = ?";

  public static final String FIND_ALL_PLANS_FOR_ADMIN_QUERY = "SELECT * FROM plan WHERE admin_id = ? ORDER BY id DESC;";

  public static final String ADD_RECIPE_TO_PLAN = "INSERT INTO recipe_plan (recipe_id, meal_name, display_order, day_name_id, plan_id) VALUES (?, ?, ?, ?, ?);";

  public static final String DELETE_RECIPE_FROM_PLAN_QUERY = "DELETE FROM recipe_plan WHERE id = ?;";
  public static final String PLAN_ID_FROM_RECIPE_PLAN = "SELECT plan_id FROM recipe_plan WHERE id = ?";


  public int setAddRecipeToPlan(int recipeId, String mealName, int displayOrder, int dayNameId, int planId) {
    try (Connection connection = DbUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_RECIPE_TO_PLAN)) {
      preparedStatement.setInt(1, recipeId);
      preparedStatement.setString(2, mealName);
      preparedStatement.setInt(3, displayOrder);
      preparedStatement.setInt(4, dayNameId);
      preparedStatement.setInt(5, planId);
      int isUpdated = preparedStatement.executeUpdate();

      if (isUpdated == 1) {
        logger.info("Recipe with id {} added to plan with id {}", recipeId, planId);

      } else {
        logger.info("Recipe with id {} NOT added to plan with id {}", recipeId, planId);
      }

      return isUpdated;

    } catch (Exception e) {
      logger.error(e.getMessage());
      return 0;
    }
  }


  /**
   * Create plan
   *
   * @param plan: Plan instance to be added to a database
   * @return Plan instance with generated id once record created successfully
   */
  public Plan create(Plan plan) {
    try (Connection connection = DbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(CREATE_PLAN_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, plan.getName());
      preparedStatement.setString(2, plan.getDescription());
      preparedStatement.setString(3, plan.getCreated());
      preparedStatement.setInt(4, plan.getAdminId());
      int result = preparedStatement.executeUpdate();

      if (result != 1) {
        throw new RuntimeException("Execute update returned " + result);
      }

      try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
        if (generatedKeys.first()) {
          plan.setId(generatedKeys.getInt(1));
          return plan;
        } else {
          throw new RuntimeException("Generated key was not found");
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Get plan by id
   *
   * @param planId: id of the plan record to be returned by sql query
   * @return Plan instance once any record retrieved from a database
   */
  public Plan read(int planId) {
    try (Connection connection = DbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(READ_PLAN_QUERY)) {
      Plan plan = new Plan();
      preparedStatement.setInt(1, planId);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          plan.setId(resultSet.getInt("id"));
          plan.setName(resultSet.getString("name"));
          plan.setDescription(resultSet.getString("description"));
          plan.setCreated(resultSet.getString("created"));
          plan.setAdminId(resultSet.getInt("admin_id"));
        }
        return plan;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  /**
   * Return all plans
   *
   * @return All plans stored in a database
   */
  public List<Plan> findAll() {
    List<Plan> plans = new ArrayList<>();

    try (Connection connection = DbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PLANS_QUERY)) {
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Plan plan = new Plan();
        plan.setId(resultSet.getInt("id"));
        plan.setName(resultSet.getString("name"));
        plan.setDescription(resultSet.getString("description"));
        plan.setCreated(resultSet.getString("created"));
        plan.setAdminId(resultSet.getInt("admin_id"));

        plans.add(plan);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return plans;
  }

  /**
   * Update plan with data from a plan object once record with given id exists in a database
   *
   * @param plan: Plan instance with id which is used to update particular record in a database
   */
  public int update(Plan plan) {
    try (Connection connection = DbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PLAN_QUERY)) {
      preparedStatement.setString(1, plan.getName());
      preparedStatement.setString(2, plan.getDescription());
      preparedStatement.setInt(3, plan.getId());

      return preparedStatement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  /**
   * Remove plan by given id
   *
   * @param planId: id of the plan to be removed
   */
  public void delete(int planId) {
    try (Connection connection = DbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PLAN_QUERY)) {
      preparedStatement.setInt(1, planId);
      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected == 0) {
        throw new NotFoundException("Plan record not found");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Remove recipe from plan by given id
   */
  public void deleteRecipeFromPlan(int recipePlanId) {
    try (Connection connection = DbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
        DELETE_RECIPE_FROM_PLAN_QUERY)) {
      preparedStatement.setInt(1, recipePlanId);
      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected == 0) {
        throw new NotFoundException("Recipe record in plan not found");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Get a list of LastAddedPlanDao objects for the last added plan for the admin with `adminId`.
   *
   * @param adminId: int value which is primary key in admins' table in a scrumlab database.
   * @return List of data transfer objects whose fields wille be used to populate view.
   */
  public PlanDto getLastAddedPlan(int adminId) {
    try (Connection connection = DbUtil.getConnection(); PreparedStatement firstStatement = connection.prepareStatement(FIND_LAST_PLAN_QUERY); PreparedStatement secondStatement = connection.prepareStatement(FIND_PLAN_DETAILS_QUERY);) {
      firstStatement.setInt(1, adminId);
      ResultSet resultSet = firstStatement.executeQuery();
      if (resultSet.next()) {
        int planId = resultSet.getInt("id");
        String planName = resultSet.getString("name");

        PlanDto lastAddedPlanDto = new PlanDto();
        lastAddedPlanDto.setName(planName);
        lastAddedPlanDto.setId(planId);


        secondStatement.setInt(1, planId);
        ResultSet result = secondStatement.executeQuery();

        while (result.next()) {
          MealDetailsDto mealDetailsDto = new MealDetailsDto();
          mealDetailsDto.setDayName(result.getString("day_name.name"));
          mealDetailsDto.setMealName(result.getString("meal_name"));
          mealDetailsDto.setRecipeId(result.getInt("recipe.id"));
          mealDetailsDto.setRecipeName(result.getString("recipe_name"));

          lastAddedPlanDto.getMealDetailsDtoList().add(mealDetailsDto);
        }
        return lastAddedPlanDto;
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public int getPlanCountByAdminId(int adminId) {
    int count = 0;
    try (Connection connection = DbUtil.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(COUNT_PLAN_QUERY)) {
        statement.setInt(1, adminId);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            count = resultSet.getInt("plan_count");
          }
        }
      }
    } catch (SQLException e) {
      logger.error(e.getMessage());
    }
    return count;
  }

  public List<PlanDto> getAllPlansForAdmin(int adminId) {
    List<PlanDto> adminPlans = new ArrayList<>();
    try (Connection connection = DbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PLANS_FOR_ADMIN_QUERY)) {
      preparedStatement.setInt(1, adminId);

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        PlanDto planDto = new PlanDto();
        planDto.setId(resultSet.getInt("id"));
        planDto.setName(resultSet.getString("name"));
        planDto.setDescription(resultSet.getString("description"));
        adminPlans.add(planDto);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return adminPlans;
  }


  public PlanDto getPlanForAdmin(int planId) {
    Plan plan = read(planId);
    if (plan != null) {
      PlanDto planDto = new PlanDto();
      planDto.setId(plan.getId());
      planDto.setName(plan.getName());
      planDto.setDescription(plan.getDescription());

      try (Connection connection = DbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(FIND_PLAN_DETAILS_QUERY)) {
        preparedStatement.setInt(1, planId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          MealDetailsDto mealDetailsDto = new MealDetailsDto();
          mealDetailsDto.setRecipePlanId(resultSet.getInt("id"));
          mealDetailsDto.setDayName(resultSet.getString("day_name.name"));
          mealDetailsDto.setMealName(resultSet.getString("meal_name"));
          mealDetailsDto.setRecipeId(resultSet.getInt("recipe.id"));
          mealDetailsDto.setRecipeName(resultSet.getString("recipe_name"));

          planDto.getMealDetailsDtoList().add(mealDetailsDto);
        }
        return planDto;

      } catch (SQLException e) {
        e.printStackTrace();
      }


    }

    return null;
  }

  public int getPlanId(int recipePlanId) {
    int planId = -1;
    try (Connection connection = DbUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            PLAN_ID_FROM_RECIPE_PLAN)) {
      preparedStatement.setInt(1, recipePlanId);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        planId = resultSet.getInt("plan_id");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return planId;
  }


}