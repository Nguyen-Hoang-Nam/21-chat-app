print('START');

db = db.getSiblingDB('chat_app');
db.createUser(
    {
        user: 'user',
        pwd: 'pass',
        roles: [{ role: 'readWrite', db: 'chat_app' }],
    },
);
db.createCollection('user');
db.createCollection('chat');

print('END');
