bundle exec middleman build
rsync -zvr --delete build/ dbs-are-fn.com:www/dbs-are-fn.com/
