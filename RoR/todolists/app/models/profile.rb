class Profile < ActiveRecord::Base
  belongs_to :user

  validate :validate_name
  validate :validate_gender
  validate :validate_sue

  def validate_name
  	if first_name.nil? && last_name.nil?
  		errors.add(:first_name, "and last name cannot both be null")
  	end
  end

  def validate_gender
  	if gender != "male" && gender != "female"
  		errors.add(:gender, "can only be male or female")
  	end
  end

  def validate_sue
  	if gender == "male" && first_name == "Sue"
  		errors.add(:first_name, "is a female name")
  	end
  end

  def self.get_all_profiles(min, max)
  	where("birth_year BETWEEN :min_age AND :max_age", min_age: min, max_age: max).order(birth_year: :asc)
  end
end
