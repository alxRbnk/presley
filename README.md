## Application Startup

1. Navigate to the directory with `docker-compose.yml`:
   ```bash
   cd /path/to/your/project
2. Start the containers with rebuild:
   ```bash
   docker-compose up --build
3. To run in the background (without logs):
   ```bash
   docker-compose up --build -d
3. To stop the containers:
   ```bash
   docker-compose down
