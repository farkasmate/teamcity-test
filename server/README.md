# TeamCity server-side configuration

## Re-generate agent config

```bash
rm agent/buildAgent.properties
pass dotenv -- docker compose up agent
```
