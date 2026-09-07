# Nova Phone Agent v0.1

First native Android shell for the Nova phone-control project.

Current:
- Stores the Nova Tailscale URL and device token.
- Native camera launch.
- Native YouTube launch.
- Native phone-file picker.
- Android permissions prepared for future voice/camera features.

Important:
The current Nova dashboard already has `/api/device-login` and an authenticated `/ws` dashboard channel, but it does not yet expose a dedicated PC→Android command channel. Therefore this v0.1 does not falsely claim that remote phone commands are already wired.

Next:
1. Add a dedicated `/ws/phone-agent` endpoint to `dashboard/server.py`.
2. Authenticate it with the existing persistent device-token mechanism.
3. Have Nova send allow-listed phone actions.
4. Let this app execute those actions.
5. Add camera capture/upload, media control, notifications, and Accessibility Service progressively.

No cloud deployment is required. The PC remains Nova's server and Tailscale remains the transport.
