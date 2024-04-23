package pl.coderslab.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dto.MealDetailsDto;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.dto.PlanDto;
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
    public static final String FIND_LAST_PLAN_QUERY = "SELECT plan.id, plan.name FROM plan JOIN admins ON admins.id = plan.admin_id WHERE admins.id = ? ORDER BY plan.id DESC LIMIT 1;";
    public static final String FIND_LAST_ADDED_PLAN_DETAILS_QUERY = """
            SELECT recipe_plan.meal_name as meal_name,
                   recipe.name           as recipe_name,
                   recipe.id,
                   day_name.name
            FROM recipe_plan
                     JOIN recipe ON recipe_plan.recipe_id = recipe.id
                     JOIN plan ON recipe_plan.plan_id = plan.id
                     JOIN day_name ON recipe_plan.day_name_id = day_name.id
            WHERE plan.id = ?
            order by day_name.display_order, recipe_plan.display_order;
                        """;

    private static final String COUNT_PLAN_QUERY = "SELECT COUNT(*) AS plan_count FROM plan WHERE admin_id = ?";

    public static final String FIND_ALL_PLANS_FOR_ADMIN = "SELECT * FROM plan WHERE admin_id = ? ORDER BY id DESC;";
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
        Plan plan = new Plan();
        try (Connection connection = DbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(READ_PLAN_QUERY)) {
            preparedStatement.setInt(1, planId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    plan.setId(resultSet.getInt("id"));
                    plan.setName(resultSet.getString("name"));
                    plan.setDescription(resultSet.getString("description"));
                    plan.setCreated(resultSet.getString("created"));
                    plan.setAdminId(resultSet.getInt("admin_id"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return plan;
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
    public void update(Plan plan) {
        try (Connection connection = DbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PLAN_QUERY)) {
            preparedStatement.setString(1, plan.getName());
            preparedStatement.setString(2, plan.getDescription());
            preparedStatement.setInt(3, plan.getId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
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
     * Get a list of LastAddedPlanDao objects for the last added plan for the admin with `adminId`.
     * @param adminId: int value which is primary key in admins' table in a scrumlab database.
     * @return List of data transfer objects whose fields wille be used to populate view.
     */
    public PlanDto getLastAddedPlan(int adminId) {
        try (Connection connection = DbUtil.getConnection(); PreparedStatement firstStatement = connection.prepareStatement(FIND_LAST_PLAN_QUERY); PreparedStatement secondStatement = connection.prepareStatement(FIND_LAST_ADDED_PLAN_DETAILS_QUERY);) {
            firstStatement.setInt(1, adminId);
            ResultSet resultSet = firstStatement.executeQuery();
            if (resultSet.next()) {
                int planId = resultSet.getInt("plan.id");
                String planName = resultSet.getString("plan.name");

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
        try (Connection connection = DbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PLANS_FOR_ADMIN)) {
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
}
