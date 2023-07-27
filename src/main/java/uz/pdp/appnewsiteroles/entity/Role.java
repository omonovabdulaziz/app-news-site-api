package uz.pdp.appnewsiteroles.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appnewsiteroles.entity.enums.Huquq;
import uz.pdp.appnewsiteroles.entity.template.AbstractEntity;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends AbstractEntity {
@Column(nullable = false, unique = true)
private String name;
@Enumerated(EnumType.STRING)
@ElementCollection(fetch = FetchType.LAZY)
private List<Huquq> huquqList;
@Column(columnDefinition = "text")
private String description;
}
