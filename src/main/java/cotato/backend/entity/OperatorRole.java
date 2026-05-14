package cotato.backend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OperatorRole {
    PART_LEADER("파트장"),
    PLANNING_TEAM_LEADER("기획팀장"),
    PR_TEAM_LEADER("홍보팀장"),
    VICE_PRESIDENT("부회장"),
    PRESIDENT("회장"),
    EDUCATION_TEAM_LEADER("교육팀장");

    private final String description;
}
