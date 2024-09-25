package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MsTBRField",schema = "dbo")
@NoArgsConstructor
public class MsTBRField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long TBR_Id;
    private String DestinationField;
    private String SourceField;
    private int FieldLength;
    private String PaddingChar;
    private String PaddingPosition;
    private String DefaultValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTBR_Id() {
        return TBR_Id;
    }

    public void setTBR_Id(Long TBR_Id) {
        this.TBR_Id = TBR_Id;
    }

    public String getDestinationField() {
        return DestinationField;
    }

    public void setDestinationField(String destinationField) {
        DestinationField = destinationField;
    }

    public String getSourceField() {
        return SourceField;
    }

    public void setSourceField(String sourceField) {
        SourceField = sourceField;
    }

    public int getFieldLength() {
        return FieldLength;
    }

    public void setFieldLength(int fieldLength) {
        FieldLength = fieldLength;
    }

    public String getPaddingChar() {
        return PaddingChar;
    }

    public void setPaddingChar(String paddingChar) {
        PaddingChar = paddingChar;
    }

    public String getPaddingPosition() {
        return PaddingPosition;
    }

    public void setPaddingPosition(String paddingPosition) {
        PaddingPosition = paddingPosition;
    }

    public String getDefaultValue() {
        return DefaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        DefaultValue = defaultValue;
    }
}
