package tn.accelengine.modules.planification.domain;

import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@PlanningSolution

public class Planning {

	@ProblemFactCollectionProperty
	private List<Ability> abilityList;
	@ProblemFactCollectionProperty
	private List<Placement> placementList;
	@ProblemFactCollectionProperty
	private List<Task> taskList;
	@ProblemFactCollectionProperty
	@ValueRangeProvider(id = "employeeRange")
	private List<User> userList;

	@PlanningEntityCollectionProperty
	private List<OperatorShift> operatorshiftList;

	@PlanningScore
	private HardSoftScore score = null;

	private Planning() {
	}

	public Planning(List<Ability> abilityList, List<Placement> placementList, List<Task> taskList, List<User> userList,
			List<OperatorShift> operatorshiftList) {

		this.abilityList = abilityList;
		this.placementList = placementList;
		this.taskList = taskList;
		this.userList = userList;
		this.operatorshiftList = operatorshiftList;
	}

	public List<Ability> getAbilityList() {
		return abilityList;
	}

	public List<Placement> getPlacementList() {
		return placementList;
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public List<OperatorShift> getOperatorshiftList() {
		return operatorshiftList;
	}

	public HardSoftScore getScore() {
		return score;
	}

}
