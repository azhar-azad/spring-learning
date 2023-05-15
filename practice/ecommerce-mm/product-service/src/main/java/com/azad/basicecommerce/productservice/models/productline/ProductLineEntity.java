package com.azad.basicecommerce.productservice.models.productline;

import com.azad.basicecommerce.productservice.models.category.CategoryEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "product_lines")
public class ProductLineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_line_id")
    private Long id;

    @Column(name = "product_line_uid", nullable = false, unique = true)
    private String productLineUid;  // productLineName

    @Column(name = "product_line_name", nullable = false)
    private String productLineName;

    @OneToMany(mappedBy = "productLine", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CategoryEntity> categories;
}
