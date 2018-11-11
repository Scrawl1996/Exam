package cn.xm.exam.bean.safehat;

import java.util.ArrayList;
import java.util.List;

public class SafehatExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SafehatExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSafehatnumIsNull() {
            addCriterion("safeHatNum is null");
            return (Criteria) this;
        }

        public Criteria andSafehatnumIsNotNull() {
            addCriterion("safeHatNum is not null");
            return (Criteria) this;
        }

        public Criteria andSafehatnumEqualTo(String value) {
            addCriterion("safeHatNum =", value, "safehatnum");
            return (Criteria) this;
        }

        public Criteria andSafehatnumNotEqualTo(String value) {
            addCriterion("safeHatNum <>", value, "safehatnum");
            return (Criteria) this;
        }

        public Criteria andSafehatnumGreaterThan(String value) {
            addCriterion("safeHatNum >", value, "safehatnum");
            return (Criteria) this;
        }

        public Criteria andSafehatnumGreaterThanOrEqualTo(String value) {
            addCriterion("safeHatNum >=", value, "safehatnum");
            return (Criteria) this;
        }

        public Criteria andSafehatnumLessThan(String value) {
            addCriterion("safeHatNum <", value, "safehatnum");
            return (Criteria) this;
        }

        public Criteria andSafehatnumLessThanOrEqualTo(String value) {
            addCriterion("safeHatNum <=", value, "safehatnum");
            return (Criteria) this;
        }

        public Criteria andSafehatnumLike(String value) {
            addCriterion("safeHatNum like", value, "safehatnum");
            return (Criteria) this;
        }

        public Criteria andSafehatnumNotLike(String value) {
            addCriterion("safeHatNum not like", value, "safehatnum");
            return (Criteria) this;
        }

        public Criteria andSafehatnumIn(List<String> values) {
            addCriterion("safeHatNum in", values, "safehatnum");
            return (Criteria) this;
        }

        public Criteria andSafehatnumNotIn(List<String> values) {
            addCriterion("safeHatNum not in", values, "safehatnum");
            return (Criteria) this;
        }

        public Criteria andSafehatnumBetween(String value1, String value2) {
            addCriterion("safeHatNum between", value1, value2, "safehatnum");
            return (Criteria) this;
        }

        public Criteria andSafehatnumNotBetween(String value1, String value2) {
            addCriterion("safeHatNum not between", value1, value2, "safehatnum");
            return (Criteria) this;
        }

        public Criteria andCreatorfullnameIsNull() {
            addCriterion("creatorFullName is null");
            return (Criteria) this;
        }

        public Criteria andCreatorfullnameIsNotNull() {
            addCriterion("creatorFullName is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorfullnameEqualTo(String value) {
            addCriterion("creatorFullName =", value, "creatorfullname");
            return (Criteria) this;
        }

        public Criteria andCreatorfullnameNotEqualTo(String value) {
            addCriterion("creatorFullName <>", value, "creatorfullname");
            return (Criteria) this;
        }

        public Criteria andCreatorfullnameGreaterThan(String value) {
            addCriterion("creatorFullName >", value, "creatorfullname");
            return (Criteria) this;
        }

        public Criteria andCreatorfullnameGreaterThanOrEqualTo(String value) {
            addCriterion("creatorFullName >=", value, "creatorfullname");
            return (Criteria) this;
        }

        public Criteria andCreatorfullnameLessThan(String value) {
            addCriterion("creatorFullName <", value, "creatorfullname");
            return (Criteria) this;
        }

        public Criteria andCreatorfullnameLessThanOrEqualTo(String value) {
            addCriterion("creatorFullName <=", value, "creatorfullname");
            return (Criteria) this;
        }

        public Criteria andCreatorfullnameLike(String value) {
            addCriterion("creatorFullName like", value, "creatorfullname");
            return (Criteria) this;
        }

        public Criteria andCreatorfullnameNotLike(String value) {
            addCriterion("creatorFullName not like", value, "creatorfullname");
            return (Criteria) this;
        }

        public Criteria andCreatorfullnameIn(List<String> values) {
            addCriterion("creatorFullName in", values, "creatorfullname");
            return (Criteria) this;
        }

        public Criteria andCreatorfullnameNotIn(List<String> values) {
            addCriterion("creatorFullName not in", values, "creatorfullname");
            return (Criteria) this;
        }

        public Criteria andCreatorfullnameBetween(String value1, String value2) {
            addCriterion("creatorFullName between", value1, value2, "creatorfullname");
            return (Criteria) this;
        }

        public Criteria andCreatorfullnameNotBetween(String value1, String value2) {
            addCriterion("creatorFullName not between", value1, value2, "creatorfullname");
            return (Criteria) this;
        }

        public Criteria andCreatorusernameIsNull() {
            addCriterion("creatorUsername is null");
            return (Criteria) this;
        }

        public Criteria andCreatorusernameIsNotNull() {
            addCriterion("creatorUsername is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorusernameEqualTo(String value) {
            addCriterion("creatorUsername =", value, "creatorusername");
            return (Criteria) this;
        }

        public Criteria andCreatorusernameNotEqualTo(String value) {
            addCriterion("creatorUsername <>", value, "creatorusername");
            return (Criteria) this;
        }

        public Criteria andCreatorusernameGreaterThan(String value) {
            addCriterion("creatorUsername >", value, "creatorusername");
            return (Criteria) this;
        }

        public Criteria andCreatorusernameGreaterThanOrEqualTo(String value) {
            addCriterion("creatorUsername >=", value, "creatorusername");
            return (Criteria) this;
        }

        public Criteria andCreatorusernameLessThan(String value) {
            addCriterion("creatorUsername <", value, "creatorusername");
            return (Criteria) this;
        }

        public Criteria andCreatorusernameLessThanOrEqualTo(String value) {
            addCriterion("creatorUsername <=", value, "creatorusername");
            return (Criteria) this;
        }

        public Criteria andCreatorusernameLike(String value) {
            addCriterion("creatorUsername like", value, "creatorusername");
            return (Criteria) this;
        }

        public Criteria andCreatorusernameNotLike(String value) {
            addCriterion("creatorUsername not like", value, "creatorusername");
            return (Criteria) this;
        }

        public Criteria andCreatorusernameIn(List<String> values) {
            addCriterion("creatorUsername in", values, "creatorusername");
            return (Criteria) this;
        }

        public Criteria andCreatorusernameNotIn(List<String> values) {
            addCriterion("creatorUsername not in", values, "creatorusername");
            return (Criteria) this;
        }

        public Criteria andCreatorusernameBetween(String value1, String value2) {
            addCriterion("creatorUsername between", value1, value2, "creatorusername");
            return (Criteria) this;
        }

        public Criteria andCreatorusernameNotBetween(String value1, String value2) {
            addCriterion("creatorUsername not between", value1, value2, "creatorusername");
            return (Criteria) this;
        }

        public Criteria andCreatordepartidIsNull() {
            addCriterion("creatorDepartId is null");
            return (Criteria) this;
        }

        public Criteria andCreatordepartidIsNotNull() {
            addCriterion("creatorDepartId is not null");
            return (Criteria) this;
        }

        public Criteria andCreatordepartidEqualTo(String value) {
            addCriterion("creatorDepartId =", value, "creatordepartid");
            return (Criteria) this;
        }

        public Criteria andCreatordepartidNotEqualTo(String value) {
            addCriterion("creatorDepartId <>", value, "creatordepartid");
            return (Criteria) this;
        }

        public Criteria andCreatordepartidGreaterThan(String value) {
            addCriterion("creatorDepartId >", value, "creatordepartid");
            return (Criteria) this;
        }

        public Criteria andCreatordepartidGreaterThanOrEqualTo(String value) {
            addCriterion("creatorDepartId >=", value, "creatordepartid");
            return (Criteria) this;
        }

        public Criteria andCreatordepartidLessThan(String value) {
            addCriterion("creatorDepartId <", value, "creatordepartid");
            return (Criteria) this;
        }

        public Criteria andCreatordepartidLessThanOrEqualTo(String value) {
            addCriterion("creatorDepartId <=", value, "creatordepartid");
            return (Criteria) this;
        }

        public Criteria andCreatordepartidLike(String value) {
            addCriterion("creatorDepartId like", value, "creatordepartid");
            return (Criteria) this;
        }

        public Criteria andCreatordepartidNotLike(String value) {
            addCriterion("creatorDepartId not like", value, "creatordepartid");
            return (Criteria) this;
        }

        public Criteria andCreatordepartidIn(List<String> values) {
            addCriterion("creatorDepartId in", values, "creatordepartid");
            return (Criteria) this;
        }

        public Criteria andCreatordepartidNotIn(List<String> values) {
            addCriterion("creatorDepartId not in", values, "creatordepartid");
            return (Criteria) this;
        }

        public Criteria andCreatordepartidBetween(String value1, String value2) {
            addCriterion("creatorDepartId between", value1, value2, "creatordepartid");
            return (Criteria) this;
        }

        public Criteria andCreatordepartidNotBetween(String value1, String value2) {
            addCriterion("creatorDepartId not between", value1, value2, "creatordepartid");
            return (Criteria) this;
        }

        public Criteria andUserhaulempidIsNull() {
            addCriterion("userHaulEmpId is null");
            return (Criteria) this;
        }

        public Criteria andUserhaulempidIsNotNull() {
            addCriterion("userHaulEmpId is not null");
            return (Criteria) this;
        }

        public Criteria andUserhaulempidEqualTo(String value) {
            addCriterion("userHaulEmpId =", value, "userhaulempid");
            return (Criteria) this;
        }

        public Criteria andUserhaulempidNotEqualTo(String value) {
            addCriterion("userHaulEmpId <>", value, "userhaulempid");
            return (Criteria) this;
        }

        public Criteria andUserhaulempidGreaterThan(String value) {
            addCriterion("userHaulEmpId >", value, "userhaulempid");
            return (Criteria) this;
        }

        public Criteria andUserhaulempidGreaterThanOrEqualTo(String value) {
            addCriterion("userHaulEmpId >=", value, "userhaulempid");
            return (Criteria) this;
        }

        public Criteria andUserhaulempidLessThan(String value) {
            addCriterion("userHaulEmpId <", value, "userhaulempid");
            return (Criteria) this;
        }

        public Criteria andUserhaulempidLessThanOrEqualTo(String value) {
            addCriterion("userHaulEmpId <=", value, "userhaulempid");
            return (Criteria) this;
        }

        public Criteria andUserhaulempidLike(String value) {
            addCriterion("userHaulEmpId like", value, "userhaulempid");
            return (Criteria) this;
        }

        public Criteria andUserhaulempidNotLike(String value) {
            addCriterion("userHaulEmpId not like", value, "userhaulempid");
            return (Criteria) this;
        }

        public Criteria andUserhaulempidIn(List<String> values) {
            addCriterion("userHaulEmpId in", values, "userhaulempid");
            return (Criteria) this;
        }

        public Criteria andUserhaulempidNotIn(List<String> values) {
            addCriterion("userHaulEmpId not in", values, "userhaulempid");
            return (Criteria) this;
        }

        public Criteria andUserhaulempidBetween(String value1, String value2) {
            addCriterion("userHaulEmpId between", value1, value2, "userhaulempid");
            return (Criteria) this;
        }

        public Criteria andUserhaulempidNotBetween(String value1, String value2) {
            addCriterion("userHaulEmpId not between", value1, value2, "userhaulempid");
            return (Criteria) this;
        }

        public Criteria andUseridcardIsNull() {
            addCriterion("userIdCard is null");
            return (Criteria) this;
        }

        public Criteria andUseridcardIsNotNull() {
            addCriterion("userIdCard is not null");
            return (Criteria) this;
        }

        public Criteria andUseridcardEqualTo(String value) {
            addCriterion("userIdCard =", value, "useridcard");
            return (Criteria) this;
        }

        public Criteria andUseridcardNotEqualTo(String value) {
            addCriterion("userIdCard <>", value, "useridcard");
            return (Criteria) this;
        }

        public Criteria andUseridcardGreaterThan(String value) {
            addCriterion("userIdCard >", value, "useridcard");
            return (Criteria) this;
        }

        public Criteria andUseridcardGreaterThanOrEqualTo(String value) {
            addCriterion("userIdCard >=", value, "useridcard");
            return (Criteria) this;
        }

        public Criteria andUseridcardLessThan(String value) {
            addCriterion("userIdCard <", value, "useridcard");
            return (Criteria) this;
        }

        public Criteria andUseridcardLessThanOrEqualTo(String value) {
            addCriterion("userIdCard <=", value, "useridcard");
            return (Criteria) this;
        }

        public Criteria andUseridcardLike(String value) {
            addCriterion("userIdCard like", value, "useridcard");
            return (Criteria) this;
        }

        public Criteria andUseridcardNotLike(String value) {
            addCriterion("userIdCard not like", value, "useridcard");
            return (Criteria) this;
        }

        public Criteria andUseridcardIn(List<String> values) {
            addCriterion("userIdCard in", values, "useridcard");
            return (Criteria) this;
        }

        public Criteria andUseridcardNotIn(List<String> values) {
            addCriterion("userIdCard not in", values, "useridcard");
            return (Criteria) this;
        }

        public Criteria andUseridcardBetween(String value1, String value2) {
            addCriterion("userIdCard between", value1, value2, "useridcard");
            return (Criteria) this;
        }

        public Criteria andUseridcardNotBetween(String value1, String value2) {
            addCriterion("userIdCard not between", value1, value2, "useridcard");
            return (Criteria) this;
        }

        public Criteria andIsdeleteIsNull() {
            addCriterion("isDelete is null");
            return (Criteria) this;
        }

        public Criteria andIsdeleteIsNotNull() {
            addCriterion("isDelete is not null");
            return (Criteria) this;
        }

        public Criteria andIsdeleteEqualTo(String value) {
            addCriterion("isDelete =", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteNotEqualTo(String value) {
            addCriterion("isDelete <>", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteGreaterThan(String value) {
            addCriterion("isDelete >", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteGreaterThanOrEqualTo(String value) {
            addCriterion("isDelete >=", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteLessThan(String value) {
            addCriterion("isDelete <", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteLessThanOrEqualTo(String value) {
            addCriterion("isDelete <=", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteLike(String value) {
            addCriterion("isDelete like", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteNotLike(String value) {
            addCriterion("isDelete not like", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteIn(List<String> values) {
            addCriterion("isDelete in", values, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteNotIn(List<String> values) {
            addCriterion("isDelete not in", values, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteBetween(String value1, String value2) {
            addCriterion("isDelete between", value1, value2, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteNotBetween(String value1, String value2) {
            addCriterion("isDelete not between", value1, value2, "isdelete");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}