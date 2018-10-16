class AddTodolistRefToUsers < ActiveRecord::Migration
  def change
    add_reference :users, :todolist, index: true, foreign_key: true
  end
end
