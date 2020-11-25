package nanshan.cj.learningnotes.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;


public enum CJArticleState implements Serializable {
    CJStart("start"),
    CJImproved("improve"),
    CJVerify("verify"),
    CJFinish("finish");

    private  String articleState;

    //enum对象构造器
    CJArticleState(String articleState){
        this.articleState = articleState;
    }

    public String getArticleState(){
        return  this.articleState;
    }

    public static CJArticleState valueOfCode(String code) {
        for (CJArticleState status : CJArticleState.values()) {
            if (status.getArticleState().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException(
                "CJArticleState status cannot be resolved for code " + code);
    }


    @Override
    public String toString() {
        return articleState;
    }
}
