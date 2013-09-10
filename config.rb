require "./lib/post"
require "sanitize"

Post.all.each do |post|
  proxy(post.url + ".html", "/post.html", :locals => {:post => post})
end

ignore "/post.html"

page "/" do
  @posts = Post.all_listed
end

# helpers do
#   def some_helper
#     "Helping"
#   end
# end

set :css_dir, 'stylesheets'

set :js_dir, 'javascripts'

set :images_dir, 'images'

activate :directory_indexes
