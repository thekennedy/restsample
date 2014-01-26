package ca.adamkennedy.codingsamples;

import java.util.Comparator;

/**
 * Custom Comparator for comparing people
 * @author akennedy
 *
 */
public enum PersonComparator implements Comparator<Person>{
	
	/**
	 * Compares people on lastname ignoring code.
	 * @see String.compareToIgnoreCase()
	 * 
	 */
	LASTNAME {
		public int compare(Person p1, Person p2) {
			
			return p1.getLastname().compareToIgnoreCase(p2.getLastname());
		}
	},
	
	/**
	 * Compares people on firstname ignoring code.
	 * @see String.compareToIgnoreCase()
	 * 
	 */
	FIRSTNAME {
		public int compare(Person p1, Person p2) {
			
			return p1.getFirstname().compareToIgnoreCase(p2.getFirstname());
		}
	},
	
	/**
	 * Compares people on id
	 * @see Integer.compare()
	 * 
	 */
	ID {
		public int compare(Person p1, Person p2) {
			return Integer.compare(p1.getId(),p2.getId());
		}
	},

	/**
	 * Compares people on age
	 * @see Double.compare()
	 * 
	 */
	AGE {
		public int compare(Person p1, Person p2) {
			return Double.compare(p1.getAge(),p2.getAge());
		}
	},
	
	/**
	 * Compares people on balance
	 * @see Double.compare()
	 * 
	 */	
	BALANCE {
		public int compare(Person p1, Person p2) {
			return Double.compare(p1.getBalance(),p2.getBalance());
		}
	};
	
	/**
	 * 
	 * @param sorts One or more PersonComparator
	 * @return compare result
	 */
	public static Comparator<Person> getComparator(final PersonComparator... sorts) {
        return new Comparator<Person>() {
            public int compare(Person o1, Person o2) {
                for (PersonComparator option : sorts) {
                    int result = option.compare(o1, o2);
                    if (result != 0) {
                        return result;
                    }
                }
                return 0;
            }
        };
    }	

}
