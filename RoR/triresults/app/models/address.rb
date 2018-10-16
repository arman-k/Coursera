class Address
    attr_accessor :city, :state, :location
    
    def initialize city=nil, state=nil, location=nil
        @city = city
        @state = state
        @location = location
    end
  
    def mongoize
        {:city=>@city, :state=>@state, :loc=>@location.mongoize}
    end
    
    def self.mongoize object
        case object
        when nil then nil        
        when Hash then Address.new(object[:city], object[:state], object[:loc]).mongoize 
        when Address then object.mongoize
        else object
        end
    end
    
    def self.demongoize object
        case object
        when nil then nil
        when Hash then Address.new(object[:city], object[:state], Point.demongoize(object[:loc]))
        else object
        end 
    end    

    def self.evolve object
        mongoize object
    end        
end
