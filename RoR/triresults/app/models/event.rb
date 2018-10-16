class Event
    include Mongoid::Document
    
    field :o, as: :order, type: Integer
    field :n, as: :name, type: String
    field :d, as: :distance, type: Float
    field :u, as: :units, type: String
    
    embedded_in :parent, polymorphic: true, touch: true
    validates :order, presence: true
    validates :name, presence: true
    
    def meters
        case units
        when 'meters' then param = 1
        when 'kilometers' then param = 1000
        when 'yards' then param = 0.9144
        when 'miles' then param = 1609.34
        else param = nil
        end
            
        if distance.nil? or param.nil?
            return nil
        else
            return distance * param
        end
    end
            
    def miles
        case units
        when 'meters' then param = 0.000621371
        when 'kilometers' then param = 0.621371
        when 'yards' then param = 0.000568182
        when 'miles' then param = 1
        else param = nil
        end
            
        if distance.nil? or param.nil?
            return nil
        else
            return distance * param
        end
    end
end
