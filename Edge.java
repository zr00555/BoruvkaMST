public class Edge implements Comparable{
        private int startPoint; //Starting node
        private int endPoint; //Connecting node
        private int weight; //Edge weight between nodes

        public Edge(int startPoint, int endPoint, int weight) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;
            this.weight = weight;
        }

        public int getStartPoint() {
            return startPoint;
        }

        public int getEndPoint() {
            return endPoint;
        }

        public int getWeight() {
            return weight;
        }

        //Create opposing edge so that the tree is bidirectional
        public Edge opposite(){
            Edge e = new Edge(endPoint, startPoint, weight);
            return e;
        }


        public boolean equals(Edge other) {
            if (this.startPoint == other.startPoint) {
                if (this.endPoint == other.endPoint) {
                    return true;
                }
            }
            return false;
        }

        //Compare edge weights to choose least cost edge
        public int compareTo(Object o) {
            Edge other = (Edge) o;
            return Double.compare(this.weight, other.weight);
        }

        public String toString() {
            return startPoint + "-(" + weight + ")- " + endPoint;
        }

    }
