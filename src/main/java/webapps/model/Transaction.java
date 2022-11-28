package webapps.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transaction", propOrder = {"System_transaction_id", "Agent_id", "Agent_transaction_id", "User_id", "Amount"})
public class Transaction {
        private String System_transaction_id;
        private Integer Agent_id;
        private String Agent_transaction_id;
        private Integer User_id;
        private Double Amount;

        public Transaction() {
        }
        public Double getAmount() {
            return Amount;
        }

        public void setAmount(Double amount) {
            Amount = amount;
        }

        public Integer getUser_id() {
            return User_id;
        }

        public void setUser_id(Integer user_id) {
            User_id = user_id;
        }

        public String getAgent_transaction_id() {
            return Agent_transaction_id;
        }

        public void setAgent_transaction_id(String agent_transaction_id) {
            Agent_transaction_id = agent_transaction_id;
        }

        public Integer getAgent_id() {
            return Agent_id;
        }

        public void setAgent_id(Integer agent_id) {
            Agent_id = agent_id;
        }

        public String getSystem_transaction_id() {
            return System_transaction_id;
        }

        public void setSystem_transaction_id(String system_transaction_id) {
            System_transaction_id = system_transaction_id;
        }

        public Transaction(String agent_transaction_id, Integer Agent_id, Double Amount) {
            this.Agent_transaction_id = agent_transaction_id;
            this.Agent_id =Agent_id;
            this.Amount = Amount;

        }


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{Agent_id=");
            sb.append(Agent_id);
            sb.append(", Amount=");
            sb.append(Amount);
            sb.append("}");
            return sb.toString();
        }


}
