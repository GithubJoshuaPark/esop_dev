package com.soro.esop.entity.base;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

import org.apache.commons.lang3.ObjectUtils;

@MappedSuperclass
public abstract class History implements Serializable {
    private static final long serialVersionUID = 1L;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "dt", column = @Column(name = "createdDt", columnDefinition = "datetime comment '생성일'", updatable = false))
                       , @AttributeOverride(name = "id", column = @Column(name = "createdId", columnDefinition = "varchar(100) comment '생성자 아이디'", updatable = false))}
    )
    protected Modif created;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumns(value = {@JoinColumn(name = "createdId", referencedColumnName = "id", insertable = false, updatable = false)}
    //            , foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
    // )
    // protected AccountMaster createdUser;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "dt", column = @Column(name = "modifiedDt", columnDefinition = "datetime comment '수정일'"))
                       , @AttributeOverride(name = "id", column = @Column(name = "modifiedId", columnDefinition = "varchar(100) comment '수정자 아이디'"))}
    )
    protected Modif modified;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumns(value = {@JoinColumn(name = "modifiedId", referencedColumnName = "id", insertable = false, updatable = false)}
    //            , foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
    // )
    // protected AccountMaster modifiedUser;

    public void created(String id) {
        if (ObjectUtils.isEmpty(this.created)) this.created = Modif.builder().dt(LocalDateTime.now()).id(id).build();
    }

    public void modified(String id) {
        if (ObjectUtils.isEmpty(this.modified)) this.modified = Modif.builder().dt(LocalDateTime.now()).id(id).build();
    }

}
