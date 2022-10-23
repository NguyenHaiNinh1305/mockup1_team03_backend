package com.itsol.recruit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Check_Point")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckPoint {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Check_Point_SEQ")
    @SequenceGenerator(name = "Check_Point_SEQ", sequenceName = "Check_Point_SEQ", allocationSize = 1, initialValue = 1)
    long id;

    @ManyToOne
    @JoinColumn(name = "affect_user_id")
    User affectUser;

    @ManyToOne
    @JoinColumn(name = "create_user_id")
    User createUser;

    @ManyToOne
    @JoinColumn(name ="checkPoint_type_id")
    CheckPointType checkPointType;

    @Column(name = "create_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    Date createDate;

    @ManyToOne
    @JoinColumn(name = "status_id")
    Status status;

    @Column(name = "note")
    String note;

    @Column(name = "salary")
    long salary;

    @ManyToOne
    @JoinColumn(name = "reviewCheck_Point_id")
    ReviewCheckPoint reviewCheckPoint;

    @Column(name = "success_salary")
    long successSalary;

    //    Thời gian hoàn thành công việc
    @Column(name = "completion_time")
    int completionTime;

    //    Chất lượng hoàn thành công việc
    @Column(name = "performance_evaluation")
    String performanceEvaluation;

    //    Báo cáo trong dự án
    @Column(name = "project_report")
    String projectReport;

    //    Kỹ năng chuyên môn
    @Column(name = "advanced_skill")
    String advancedSkill;

    //    Tinh thần học hỏi và cầu tiến
    @Column(name = "studious_spirit")
    String studiousSpirit;

    //  Khả năng hiểu nghiệp vụ
    @Column(name = "ability_profession")
    String abilityProfession;

    //  Khả năng ứng biến với công việc mới
    @Column(name = "Ability_Improvise_New_Work")
    String AbilityImproviseNewWork;

    //  Chấp hành giờ giấc
    @Column(name = "adhere_Time")
    String adhereTime;

    //  Kỹ năng giao tiếp, trao đổi
    @Column(name = "communication_Skills")
    String communicationSkills;

    @Column(name = "onsite")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean onsite;

    //  Kỹ năng giao tiếp, trao đổi
    @Column(name = "customer_Reviews")
    String customerReviews;

    //  Sẵn sàng onsite
    @Column(name = "ready_Onsite")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean readyOnsite;

    //  Sẵn sàng nhận dự án
    @Column(name = "ready_OnProject")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean readyOnProject;

    //  Các điều cần đạt được trong thời gian tới, định hướng bản thân
    @Column(name = "self_Orientation")
    String selfOrientation;

    //  Góp ý cho dự án, công ty
    @Column(name = "feedback_Company")
    String feedbackCompany;
}
